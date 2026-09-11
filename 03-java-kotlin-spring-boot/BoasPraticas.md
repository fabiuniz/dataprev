# 📋 CHECKLIST DEFINITIVO: INTEROPERABILIDADE JAVA / KOTLIN / SPRING

## 📂 1. Estrutura de Arquivos e SOLID
* [ ] **Um Arquivo, Uma Classe:** Crie sempre um arquivo físico isolado para cada classe ou interface pública, mesmo que o Kotlin permita agrupar tudo em um arquivo só.
* [ ] **Pastas Espelhadas:** Mantenha a mesma árvore de subpastas (ex: `domain/`, `strategy/`) tanto para `.java` quanto para `.kt` para não quebrar o padrão Strategy.

## 🔒 2. Visibilidade e Escopo
* [ ] **Xerife do Java:** Lembre-se de digitar a palavra `public` no Java se a interface/classe precisar ser vista por subpastas. O padrão do Java é trancar no pacote (*package-private*).
* [ ] **Padrão do Kotlin:** Lembre-se de que no Kotlin tudo já nasce `public`. Se quiser esconder algo no mesmo pacote, use o modificador `internal`.

## 🧬 3. Herança e Extensões
* [ ] **Classes Abertas:** Se precisar estender uma classe Kotlin no Java, você é obrigado a marcá-la com a palavra-chave `open class` no Kotlin (já que lá elas nascem fechadas/`final`).
* [ ] **Substituição de Liskov (LSP):** Garanta contratos idênticos. Não mude o comportamento esperado das assinaturas dos métodos ao transitar entre as linguagens.

## 🍃 4. Framework (Spring Boot)
* [ ] **Injeção Limpa:** Use injeção de dependência via construtor. O Spring resolve isso de forma idêntica tanto no Java quanto no Kotlin, dispensando o uso do `@Autowired`.
* [ ] **Tratamento de Nulos:** Atente-se ao *Null Safety* do Kotlin. Parâmetros injetados que podem vir vazios do ecossistema Java precisam do caractere `?` no tipo do Kotlin.

---

## 🚀 Comando de Limpeza (Obrigatório antes de virar a chave)
Sempre limpe os resíduos de compilação da linguagem anterior antes de testar o build da outra:
mvn clean compile


<!--
require -> No Kotlin, você diz o que espera que aconteça (valor <= 300.0).
if -> No Java, você diz o que não pode acontecer (if (valor > 300.0)).
-->