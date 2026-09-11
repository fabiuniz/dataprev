# 🗂️ Guia de Revisão Técnica (Nível Sênior/Especialista) - TQI

## 🧩 1. Princípios de Design & Arquitetura (O Coração do Código)
* [ ] **S - Responsabilidade Única (SRP):** Uma classe deve ter apenas um motivo para mudar. (Ex: Isolar o `NotificadorService` do `CheckoutService`).
* [ ] **O - Aberto/Fechado (OCP):** O sistema deve ser aberto para novas extensões (regras de negócio) e fechado para alterações. (Uso de *Strategy Pattern* para criar novas formas de pagamento sem quebrar as antigas).
* [ ] **L - Substituição de Liskov (LSP):** As classes filhas devem poder substituir as classes pai sem quebrar o sistema ou lançar exceções inesperadas.
* [ ] **I - Segregação de Interfaces (ISP):** É melhor criar interfaces específicas (como `Estornavel`) em vez de interfaces monstros que forçam as classes a implementarem métodos vazios/inúteis.
* [ ] **D - Inversão de Dependência (DIP):** O core de negócio deve depender de interfaces (abstrações) e não de implementações concretas, permitindo que o Spring injete as peças.
* [ ] **Arquitetura Hexagonal (Ports & Adapters):** Isolamento total do domínio de negócio (core) contra agentes e tecnologias externas (como banco de dados, brokers de mensageria ou APIs de IA).

## 🔄 2. Mensageria, Resiliência & Concorrência (Sistemas de Missão Crítica)
* [ ] **Garantia de Processamento na Fila:** O broker (Kafka/RabbitMQ) só remove a mensagem após o processamento com sucesso e o envio do *Acknowledgment* (ACK) manual pela aplicação.
* [ ] **Idempotência vs. Chave Única:** Idempotência é o objetivo de não duplicar transações. A Chave Única (`UNIQUE CONSTRAINT` no banco SQL) é o mecanismo atômico definitivo para travar concorrências no mesmo milissegundo.
* [ ] **Circuit Breaker (Disjuntor):** Padrão que "abre o disjuntor" para isolar uma API de terceiro que está instável ou lenta, respondendo imediatamente com um fallback para proteger a saúde da sua aplicação.
* [ ] **Virtual Threads (Java 21) / Kotlin Coroutines:** Uso de threads virtuais e leves para lidar com alta concorrência (TPS elevado) de forma assíncrona, escalando o sistema sem estourar a memória do servidor.

## 📊 3. Observabilidade, Banco de Dados & Performance
* [ ] **Os 3 Pilares da Observabilidade:** * *Métricas:* Saúde e performance do servidor (Prometheus/Grafana).
    * *Logs Estruturados:* Logs em formato JSON contendo o contexto completo do erro.
    * *Distributed Tracing:* Rastreamento ponta a ponta da requisição entre microsserviços via `Trace ID` (OpenTelemetry/Jaeger).
* [ ] **Estratégia de Cache (Redis):** Armazenamento em memória de dados muito lidos e pouco alterados para aliviar o banco relacional.
* [ ] **Read/Write Splitting:** Arquitetura de banco de dados com uma instância Master dedicada a escritas/alterações e réplicas Slaves focadas apenas em leituras pesadas e relatórios.1