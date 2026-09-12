package com.dprev.checkout.service;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.dprev.checkout.domain.MetodoPagamento;
import com.dprev.checkout.domain.pix.PixPagamento;
import com.dprev.checkout.domain.cartao.CartaoCreditoPagamento;
import com.dprev.checkout.domain.boleto.BoletoPagamento;
import com.dprev.checkout.domain.vr.ValeRefeicaoPagamento;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {
    private final CheckoutService checkoutService;

    public WebServer(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    public void iniciar(int porta) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
        
        // Rota 1: Serve a página HTML/CSS estática de forma embarcada
        server.createContext("/", new ExibirTelaHandler());
        // Rota 2: Endpoint API REST que recebe o POST do JavaScript
        server.createContext("/api/checkout", new ApiCheckoutHandler(checkoutService));
        
        // ⚡ SOLUÇÃO DE CONCORRÊNCIA: Agora o servidor aguenta múltiplas requisições paralelas sem travar!
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(50)); 
        
        System.out.println("🌍 [SERVIDOR WEB] Rodando com sucesso em http://localhost:" + porta);
        server.start();
    }

    // Handler corrigido para entregar o arquivo de dentro do JAR / pasta pública
    static class ExibirTelaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Tenta buscar no classpath ou na raiz do projeto alternativamente
            InputStream is = WebServer.class.getResourceAsStream("/public/index.html");
            byte[] resposta;
            
            if (is != null) {
                resposta = is.readAllBytes();
                is.close();
            } else {
                // Fallback para leitura direta local caso rode via IDE sem empacotar
                java.io.File file = new java.io.File("public/index.html");
                if (file.exists()) {
                    resposta = java.nio.file.Files.readAllBytes(file.toPath());
                } else {
                    String erro = "❌ Arquivo index.html não encontrado no JAR ou no caminho local.";
                    exchange.sendResponseHeaders(404, erro.length());
                    exchange.getResponseBody().write(erro.getBytes());
                    exchange.getResponseBody().close();
                    return;
                }
            }

            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, resposta.length);
            OutputStream os = exchange.getResponseBody();
            os.write(resposta);
            os.close();
        }
    }

    // Handler da API REST (Consome JSON e executa o CheckoutService)
    static class ApiCheckoutHandler implements HttpHandler {
        private final CheckoutService checkoutService;

        public ApiCheckoutHandler(CheckoutService checkoutService) {
            this.checkoutService = checkoutService;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Habilita CORS para o navegador não bloquear a requisição
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String corpo = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                
                String email = extrairValorJson(corpo, "email");
                String valorStr = extrairValorJson(corpo, "valor");
                String metodoStr = extrairValorJson(corpo, "metodo");

                double valor = 0.0;
                try {
                    if (!valorStr.isEmpty()) valor = Double.parseDouble(valorStr);
                } catch (NumberFormatException e) {
                    enviarResposta(exchange, "Valor numérico inválido.", 400);
                    return;
                }

                MetodoPagamento metodo = switch (metodoStr.toLowerCase().trim()) {
                    case "pix" -> new PixPagamento();
                    case "cartao" -> new CartaoCreditoPagamento();
                    case "boleto" -> new BoletoPagamento();
                    case "vr" -> new ValeRefeicaoPagamento();
                    default -> null;
                };

                String respostaTexto;
                int statusCode;

                try {
                    if (metodo == null) throw new IllegalArgumentException("Método de pagamento inválido.");
                    
                    checkoutService.finalizarPedido(valor, metodo, email);
                    respostaTexto = "Checkout finalizado com sucesso via " + metodoStr.toUpperCase() + "!";
                    statusCode = 200;
                } catch (Exception e) {
                    respostaTexto = e.getMessage();
                    statusCode = 400;
                }

                enviarResposta(exchange, respostaTexto, statusCode);
            }
        }

        private void enviarResposta(HttpExchange exchange, String texto, int statusCode) throws IOException {
            byte[] bytesResposta = texto.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, bytesResposta.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytesResposta);
            os.close();
        }

        private String extrairValorJson(String json, String chave) {
            int index = json.indexOf("\"" + chave + "\"");
            if (index == -1) return "";
            int inicio = json.indexOf(":", index) + 1;
            int fim = json.indexOf(",", inicio);
            if (fim == -1) fim = json.indexOf("}", inicio);
            return json.substring(inicio, fim).replace("\"", "").replace("{", "").replace("}", "").trim();
        }
    }
}