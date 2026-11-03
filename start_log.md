```bash
📁 solid_hybrid/
│   ├── 📁 01-java-puro/
			🧹 Parando todos os containers Docker da máquina...
			🧹 Encerrando instâncias antigas da aplicação...
			⏹️  Container pg-checkout existe mas está parado. Iniciando...
			⏹️  Container redis-checkout existe mas está parado. Iniciando...
			⏹️  Container localstack-checkout existe mas está parado. Iniciando...
			🧹 Limpando resíduos antigos...
			🚀 Compilando código de Produção (Main)...
			🧪 Compilando código de Testes (Test)...
			📦 Empacotando tudo em JAR Executável...
			⏳ Aguardando os serviços inicializarem completamente...
			✅ PostgreSQL está pronto!
			✅ Redis está pronto!
			⏳ Aguardando LocalStack responder...
			✅ LocalStack (AWS SQS) está pronto!
			📦 Fila 'fila-notificacao-checkout' provisionada com sucesso no LocalStack.
			🏃 Executando a aplicação nativa híbrida (Main)...

			🚀 ========================================================
			🚀 INICIALIZANDO ECOSSISTEMA FULL-STACK NATIVO
			🚀 ========================================================
			🧪 Executando Testes de Integração (CheckoutSOLIDTest)...

			🧪 ========================================================
			🧪 INICIANDO SUITE DE TESTES COMPLETA DE SOLID (JAVA PURO)
			🧪 ========================================================

			🔹 [CENÁRIO 1] Processamento Polimórfico Padrão...
			--- Iniciando Checkout ---
			⚡PIX: Gerando QR Code no valor de R$ 100.0
			🛢️[SQLITE DB] Abrindo conexão com o arquivo: pagamentos.db
			🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('PixPagamento');
			🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			💳Cartão: Transacionando R$ 250.5 na operadora.
			🛢️[SQLITE DB] Abrindo conexão com o arquivo: pagamentos.db
			🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('CartaoCreditoPagamento');
			🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			📄Boleto: Gerando linha digitável para o valor de R$ 50.0
			🛢️[SQLITE DB] Abrindo conexão com o arquivo: pagamentos.db
			🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('BoletoPagamento');
			🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!
			--- Checkout Finalizado ---

			✅ Polimorfismo básico executado sem erros.

			🔹 [CENÁRIO 2] Extensões Complexas e Contratos do Cartão Premium...
			--- Iniciando Checkout ---
			🧱Cartão Base: Processando R$ 5000.0 na bandeira VISA
			🛢️[SQLITE DB] Abrindo conexão com o arquivo: pagamentos.db
			🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('CartaoCorporativoPremium');
			🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!
			--- Checkout Finalizado ---

			📝[LOG AUDITORIA] Transação registrada no valor de R$ 5000.0
			👑Corporativo Premium: Convertendo taxa para USD
			🚨👑Corporativo Premium: Estornando teto de R$ 5000.0 com prioridade.
			✅ Validação de recursos estendidos do Cartão Corporativo concluída.

			🔹 [CENÁRIO 3] Análise de Quebra do Liskov Substitution Principle...
			--- Iniciando Checkout ---
			🍔VR: Processando R$ 50.0 via cartao beneficio.
			🛢️[SQLITE DB] Abrindo conexão com o arquivo: pagamentos.db
			🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('ValeRefeicaoPagamento');
			🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!
			--- Checkout Finalizado ---

			✅ VR Valor Baixo: Funcionou conforme esperado.
			--- Iniciando Checkout ---
			✅ SUCESSO AUTOMATIZADO: Comportamento de exceção oculta do VR (LSP)
			🌍 [SERVIDOR WEB] Rodando com sucesso em http://localhost:8080



│   ├── 📁 02-java-spring-boot/
			🧹 Parando todos os containers Docker da máquina...
			69059cdf084c
			101a9666c7f0
			dc9dc7cc5db3
			🗑️ Removendo containers antigos para evitar conflitos...
			69059cdf084c
			101a9666c7f0
			dc9dc7cc5db3
			🧹 Encerrando instâncias antigas da aplicação...
			🐳 Verificando infraestrutura em containers para a Task 02 (Spring Boot Puro)...
			🚀 Criando container PostgreSQL...
			[1]+  Exit 143                java -cp "$FOLDER_JAR/checkout-solid.jar:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR" com.dprev.checkout.Main  (wd: /home/userlnx/docker/script_docker/java-ia/solid_hybrid/01-java-puro)
			(wd now: /home/userlnx/docker/script_docker/java-ia/solid_hybrid/02-java-spring-boot)
			🚀 Criando container Redis...
			☁️ Criando container LocalStack (SQS)...
			⏳ Aguardando o PostgreSQL ficar pronto...
			✅ PostgreSQL está pronto!
			⏳ Aguardando o Redis responder...
			✅ Redis está pronto!
			⏳ Aguardando LocalStack responder...
			✅ LocalStack (AWS SQS) está pronto!
			📦 Fila 'fila-notificacao-checkout' provisionada no LocalStack.
			🏃 Compilando e executando o ecossistema Spring Boot...
			[INFO] Scanning for projects...
			[INFO]
			[INFO] -------------------------< com.dprev:checkout >-------------------------
			[INFO] Building checkout-solid-spring 0.0.1-SNAPSHOT
			[INFO]   from pom.xml
			[INFO] --------------------------------[ jar ]---------------------------------
			[INFO]
			[INFO] --- clean:3.3.2:clean (default-clean) @ checkout ---
			[INFO] Deleting /home/userlnx/docker/script_docker/java-ia/solid_hybrid/02-java-spring-boot/target
			[INFO]
			[INFO] >>> spring-boot:3.2.5:run (default-cli) > test-compile @ checkout >>>
			[INFO]
			[INFO] --- resources:3.3.1:resources (default-resources) @ checkout ---
			[INFO] Copying 1 resource from src/main/resources to target/classes
			[INFO] Copying 1 resource from src/main/resources to target/classes
			[INFO]
			[INFO] --- compiler:3.11.0:compile (default-compile) @ checkout ---
			[INFO] Changes detected - recompiling the module! :source
			[INFO] Compiling 17 source files with javac [debug release 17] to target/classes
			[INFO]
			[INFO] --- resources:3.3.1:testResources (default-testResources) @ checkout ---
			[INFO] skip non existing resourceDirectory /home/userlnx/docker/script_docker/java-ia/solid_hybrid/02-java-spring-boot/src/test/resources
			[INFO]
			[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ checkout ---
			[INFO] No sources to compile
			[INFO]
			[INFO] <<< spring-boot:3.2.5:run (default-cli) < test-compile @ checkout <<<
			[INFO]
			[INFO]
			[INFO] --- spring-boot:3.2.5:run (default-cli) @ checkout ---
			[INFO] Attaching agents: []

			  .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			  '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::                (v3.2.5)

			2026-09-14T14:42:42.153-04:00  INFO 48171 --- [           main] com.dprev.checkout.Main                  : Starting Main using Java 21.0.11 with PID 48171 (/home/userlnx/docker/script_docker/java-ia/solid_hybrid/02-java-spring-boot/target/classes started by root in /home/userlnx/docker/script_docker/java-ia/solid_hybrid/02-java-spring-boot)
			2026-09-14T14:42:42.156-04:00  INFO 48171 --- [           main] com.dprev.checkout.Main                  : No active profile set, falling back to 1 default profile: "default"
			2026-09-14T14:42:43.899-04:00  INFO 48171 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
			2026-09-14T14:42:43.960-04:00  INFO 48171 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 39 ms. Found 0 JPA repository interfaces.
			2026-09-14T14:42:44.667-04:00  INFO 48171 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
			2026-09-14T14:42:44.688-04:00  INFO 48171 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
			2026-09-14T14:42:44.688-04:00  INFO 48171 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
			2026-09-14T14:42:44.754-04:00  INFO 48171 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
			2026-09-14T14:42:44.756-04:00  INFO 48171 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2535 ms
			2026-09-14T14:42:45.148-04:00  INFO 48171 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
			2026-09-14T14:42:45.329-04:00  INFO 48171 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
			2026-09-14T14:42:45.398-04:00  INFO 48171 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
			2026-09-14T14:42:45.986-04:00  INFO 48171 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
			2026-09-14T14:42:46.065-04:00  INFO 48171 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
			2026-09-14T14:42:46.368-04:00  INFO 48171 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@4bc9ca97
			2026-09-14T14:42:46.370-04:00  INFO 48171 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
			2026-09-14T14:42:47.089-04:00  INFO 48171 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
			2026-09-14T14:42:47.096-04:00  INFO 48171 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
			2026-09-14T14:42:47.203-04:00  WARN 48171 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
			2026-09-14T14:42:47.242-04:00  INFO 48171 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
			2026-09-14T14:42:47.714-04:00  INFO 48171 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
			2026-09-14T14:42:47.725-04:00  INFO 48171 --- [           main] com.dprev.checkout.Main                  : Started Main in 6.497 seconds (process running for 7.426)

			🧪 ========================================================
			🧪 INICIANDO SUITE DE TESTES AUTOMATIZADOS (SPRING BOOT)
			🧪 ========================================================

			🔹 [CENÁRIO 1] Processamento Polimórfico Padrão...
			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$100.0
			E-mail enviado para fabiano@email.com confirmando os R$ 100.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Cartão: Transacionando R$ 250.5 na operadora.
			E-mail enviado para fabiano@email.com confirmando os R$ 250.5
			--- Checkout Finalizado ---

			🔹 [CENÁRIO 2] Extensões Complexas e Contratos do Cartão Premium...
			--- Iniciando Checkout ---
			Cartão Base: Processando R$ 5000.0 na bandeira VISA
			E-mail enviado para diretoria@dprev.com confirmando os R$ 5000.0
			--- Checkout Finalizado ---

			[LOG AUDITORIA] Transação registrada no valor de R$ 5000.0
			Corporativo Premium: Convertendo taxa para USD
			Corporativo Premium: Estornando teto de R$ 5000.0 com prioridade.

			🔹 [CENÁRIO 3] Análise de Quebra do Liskov Substitution Principle (LSP)...
			--- Iniciando Checkout ---
			VR: Processando R$50.0 via cartao beneficio.
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			[ALERTA ERRO] LSP Quebrado sob análise: Limite de transacao VR excedido!
			✅ SUCESSO AUTOMATIZADO: O Spring tratou a exceção do polimorfismo restrito.

			🚀 ========================================================
			🚀 TESTES CONCLUÍDOS. O SERVIDOR CONTINUA ATIVO NA PORTA 8080.
			🚀 ========================================================


│   ├── 📁 03-java-kotlin-spring-boot/
			🧹 Parando todos os containers Docker da máquina...
			🗑️ Removendo containers antigos para evitar conflitos...
			🧹 Encerrando instâncias antigas da aplicação...
			🐳 Verificando infraestrutura em containers para a Task 03 (Java + Kotlin + Spring Boot)...
			🚀 Criando container PostgreSQL...
			🚀 Criando container Redis...
			☁️ Criando container LocalStack (SQS)...
			⏳ Aguardando o PostgreSQL ficar pronto...
			✅ PostgreSQL está pronto!
			⏳ Aguardando o Redis responder...
			✅ Redis está pronto!
			⏳ Aguardando LocalStack responder...
			✅ LocalStack (AWS SQS) está pronto!
			📦 Fila 'fila-notificacao-checkout' provisionada no LocalStack.
			🏃 Compilando e executando o ecossistema Spring Boot via Maven...
			[INFO] Scanning for projects...
			[INFO]
			[INFO] -------------------------< com.dprev:checkout >-------------------------
			[INFO] Building checkout-solid-kotlin 0.0.1-SNAPSHOT
			[INFO]   from pom.xml
			[INFO] --------------------------------[ jar ]---------------------------------
			[INFO]
			[INFO] --- clean:3.3.2:clean (default-clean) @ checkout ---
			[INFO] Deleting /home/userlnx/docker/script_docker/java-ia/solid_hybrid/03-java-kotlin-spring-boot/target
			[INFO]
			[INFO] >>> spring-boot:3.2.5:run (default-cli) > test-compile @ checkout >>>
			[INFO]
			[INFO] --- resources:3.3.1:resources (default-resources) @ checkout ---
			[INFO] Copying 1 resource from src/main/resources to target/classes
			[INFO] Copying 1 resource from src/main/resources to target/classes
			[INFO]
			[INFO] --- kotlin:1.9.23:compile (compile) @ checkout ---
			[INFO] Applied plugin: 'spring'
			[WARNING] /home/userlnx/docker/script_docker/java-ia/solid_hybrid/03-java-kotlin-spring-boot/src/main/kotlin/com/dprev/checkout/service/EmailNotificadorService.kt: (9, 43) The corresponding parameter in the supertype 'Notificador' is named 'destino'. This may cause problems when calling this function with named arguments.
			[INFO]
			[INFO] --- compiler:3.11.0:compile (java-compile) @ checkout ---
			[INFO] Changes detected - recompiling the module! :source
			[INFO] Compiling 2 source files with javac [debug release 17] to target/classes
			[INFO]
			[INFO] --- resources:3.3.1:testResources (default-testResources) @ checkout ---
			[INFO] skip non existing resourceDirectory /home/userlnx/docker/script_docker/java-ia/solid_hybrid/03-java-kotlin-spring-boot/src/test/resources
			[INFO]
			[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ checkout ---
			[INFO] No sources to compile
			[INFO]
			[INFO] --- kotlin:1.9.23:test-compile (test-compile) @ checkout ---
			[INFO] No sources to compile
			[INFO]
			[INFO] <<< spring-boot:3.2.5:run (default-cli) < test-compile @ checkout <<<
			[INFO]
			[INFO]
			[INFO] --- spring-boot:3.2.5:run (default-cli) @ checkout ---
			[INFO] Attaching agents: []

			  .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			  '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::                (v3.2.5)

			2026-09-14T20:01:14.187-04:00  INFO 131906 --- [           main] com.dprev.checkout.Application           : Starting Application using Java 21.0.11 with PID 131906 (/home/userlnx/docker/script_docker/java-ia/solid_hybrid/03-java-kotlin-spring-boot/target/classes started by root in /home/userlnx/docker/script_docker/java-ia/solid_hybrid/03-java-kotlin-spring-boot)
			2026-09-14T20:01:14.190-04:00  INFO 131906 --- [           main] com.dprev.checkout.Application           : No active profile set, falling back to 1 default profile: "default"
			2026-09-14T20:01:14.885-04:00  INFO 131906 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
			2026-09-14T20:01:14.910-04:00  INFO 131906 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 17 ms. Found 0 JPA repository interfaces.
			2026-09-14T20:01:15.452-04:00  INFO 131906 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
			2026-09-14T20:01:15.466-04:00  INFO 131906 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
			2026-09-14T20:01:15.467-04:00  INFO 131906 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
			2026-09-14T20:01:15.522-04:00  INFO 131906 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
			2026-09-14T20:01:15.524-04:00  INFO 131906 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1269 ms
			2026-09-14T20:01:15.686-04:00  INFO 131906 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
			2026-09-14T20:01:15.742-04:00  INFO 131906 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
			2026-09-14T20:01:15.784-04:00  INFO 131906 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
			2026-09-14T20:01:16.034-04:00  INFO 131906 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
			2026-09-14T20:01:16.063-04:00  INFO 131906 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
			2026-09-14T20:01:16.249-04:00  INFO 131906 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@103c97ff
			2026-09-14T20:01:16.251-04:00  INFO 131906 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
			2026-09-14T20:01:16.673-04:00  INFO 131906 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
			2026-09-14T20:01:16.679-04:00  INFO 131906 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
			2026-09-14T20:01:17.098-04:00  WARN 131906 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
			2026-09-14T20:01:17.130-04:00  INFO 131906 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
			2026-09-14T20:01:17.539-04:00  INFO 131906 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
			2026-09-14T20:01:17.549-04:00  INFO 131906 --- [           main] com.dprev.checkout.Application           : Started Application in 3.795 seconds (process running for 4.171)

			=== TESTANDO ESTRUTURA ORIGINAL VIA SPRING + KOTLIN ===
			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 100.0
			E-mail enviado para fabiano@email.com confirmando os R$ 100.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Cartão: Transacionando R$ 250.5 na operadora.
			E-mail enviado para fabiano@email.com confirmando os R$ 250.5
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Boleto: Gerando linha digitável para o valor de R$ 50.0
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			=== TESTANDO CONCEITOS DE CASCATA (EXTENDS & IMPLEMENTS) ===
			--- Iniciando Checkout ---
			Cartão Base: Processando R$ 5000.0 na bandeira Visa
			E-mail enviado para diretoria@dprev.com confirmando os R$ 5000.0
			--- Checkout Finalizado ---

			Verificando rastreabilidade do cartão corporativo:
			[LOG AUDITORIA] Transação registrada no valor de R$ 5000.0
			Corporativo Premium: Convertendo taxa para USD
			Corporativo Premium: Estornando teto de R$ 5000.0 com prioridade.

			=== TESTANDO VIOLAÇÃO DO LSP (ESTUDO DE CASO) ===
			--- Iniciando Checkout ---
			VR: Processando R$ 50.0 via cartao beneficio.
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			[ALERTA ERRO] LSP Quebrado sob análise: Limite de transacao VR excedido!
			O código cliente não deve ser forçado a prever regras restritivas exclusivas de um subtipo.


│   ├── 📁 04-kotlin-spring-boot/
			🔍 Liberando a porta 8080...
			🧹 Parando todos os containers Docker da máquina...
			🗑️ Removendo containers antigos para evitar conflitos...
			🚀 Iniciando a aplicação Spring Boot...
			[INFO] Scanning for projects...
			[INFO] Kotlin smart defaults are enabled for checkout
			[INFO]
			[INFO] -------------------------< com.dprev:checkout >-------------------------
			[INFO] Building checkout-solid-spring 0.0.1-SNAPSHOT
			[INFO]   from pom.xml
			[INFO] --------------------------------[ jar ]---------------------------------
			[INFO]
			[INFO] >>> spring-boot:3.2.5:run (default-cli) > test-compile @ checkout >>>
			[INFO]
			[INFO] --- kotlin:2.4.0:kapt (default-kapt) @ checkout ---
			[WARNING] Source root doesn't exist: /home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot/src/main/java
			[INFO] Applied plugin: 'spring'
			[INFO] starting the daemon as: /usr/lib/jvm/java-21-openjdk-amd64/bin/java -cp /root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-impl/2.4.0/kotlin-build-tools-impl-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-api/2.4.0/kotlin-build-tools-api-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/2.4.0/kotlin-stdlib-2.4.0.jar:/root/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-cri-impl/2.4.0/kotlin-build-tools-cri-impl-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-compiler-embeddable/2.4.0/kotlin-compiler-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-script-runtime/2.4.0/kotlin-script-runtime-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.6.10/kotlin-reflect-1.6.10.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-daemon-embeddable/2.4.0/kotlin-daemon-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.8.0/kotlinx-coroutines-core-jvm-1.8.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-compiler-runner/2.4.0/kotlin-compiler-runner-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-daemon-client/2.4.0/kotlin-daemon-client-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-compiler-embeddable/2.4.0/kotlin-scripting-compiler-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-common/2.4.0/kotlin-scripting-common-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-jvm/2.4.0/kotlin-scripting-jvm-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-compiler-impl-embeddable/2.4.0/kotlin-scripting-compiler-impl-embeddable-2.4.0.jar -Djava.awt.headless=true -Djava.rmi.server.hostname=127.0.0.1 -Xmx982m -XX:ReservedCodeCacheSize=320m -ea -Dkotlin.daemon.custom.run.files.path.for.tests=/root/.local/share/kotlin/daemon -XX:+UseCodeCacheFlushing -XX:+UseParallelGC -Dkotlin.daemon.initiator.marker.file=/tmp/kotlin-compiler-client-1440650366053728929-is-running --add-exports java.base/sun.nio.ch=ALL-UNNAMED org.jetbrains.kotlin.daemon.KotlinCompileDaemon --daemon-logsPath /tmp --daemon-logsFileSizeLimit=1048576 --daemon-logsFileCountLimit=3 --daemon-runFilesPath /root/.local/share/kotlin/daemon --daemon-autoshutdownIdleSeconds=7200 --daemon-shutdownDelayMilliseconds=1800000 --daemon-compilerClasspath /root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-impl/2.4.0/kotlin-build-tools-impl-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-api/2.4.0/kotlin-build-tools-api-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/2.4.0/kotlin-stdlib-2.4.0.jar:/root/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-build-tools-cri-impl/2.4.0/kotlin-build-tools-cri-impl-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-compiler-embeddable/2.4.0/kotlin-compiler-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-script-runtime/2.4.0/kotlin-script-runtime-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.6.10/kotlin-reflect-1.6.10.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-daemon-embeddable/2.4.0/kotlin-daemon-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.8.0/kotlinx-coroutines-core-jvm-1.8.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-compiler-runner/2.4.0/kotlin-compiler-runner-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-daemon-client/2.4.0/kotlin-daemon-client-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-compiler-embeddable/2.4.0/kotlin-scripting-compiler-embeddable-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-common/2.4.0/kotlin-scripting-common-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-jvm/2.4.0/kotlin-scripting-jvm-2.4.0.jar:/root/.m2/repository/org/jetbrains/kotlin/kotlin-scripting-compiler-impl-embeddable/2.4.0/kotlin-scripting-compiler-impl-embeddable-2.4.0.jar
			[INFO] #1 retrying connecting to the daemon
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- resources:3.3.1:resources (default-resources) @ checkout ---
			[INFO] Copying 0 resource from src/main/resources to target/classes
			[INFO] Copying 1 resource from src/main/resources to target/classes
			[INFO]
			[INFO] --- kotlin:2.4.0:compile (default-compile) @ checkout ---
			[WARNING] Source root doesn't exist: /home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot/src/main/java
			[INFO] Applied plugin: 'spring'
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- kotlin:2.4.0:compile (compile) @ checkout ---
			[WARNING] Source root doesn't exist: /home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot/src/main/java
			[INFO] Applied plugin: 'spring'
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- compiler:3.11.0:compile (compile) @ checkout ---
			[INFO] Nothing to compile - all classes are up to date
			[INFO]
			[INFO] --- kotlin:2.4.0:test-kapt (default-test-kapt) @ checkout ---
			[INFO] Applied plugin: 'spring'
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- resources:3.3.1:testResources (default-testResources) @ checkout ---
			[INFO] skip non existing resourceDirectory /home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot/src/test/resources
			[INFO]
			[INFO] --- kotlin:2.4.0:test-compile (default-test-compile) @ checkout ---
			[INFO] Applied plugin: 'spring'
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- kotlin:2.4.0:test-compile (test-compile) @ checkout ---
			[INFO] Applied plugin: 'spring'
			[INFO] Options for KOTLIN DAEMON: CompilationOptions(compilerMode=NON_INCREMENTAL_COMPILER, targetPlatform=JVM, reportCategories=[0], reportSeverity=2, requestedCompilationResults=[0], kotlinScriptExtensions=null, generateCompilerRefIndex=false)
			[INFO]
			[INFO] --- compiler:3.11.0:testCompile (testCompile) @ checkout ---
			[INFO] Changes detected - recompiling the module! :dependency
			[INFO]
			[INFO] <<< spring-boot:3.2.5:run (default-cli) < test-compile @ checkout <<<
			[INFO]
			[INFO]
			[INFO] --- spring-boot:3.2.5:run (default-cli) @ checkout ---
			[INFO] Attaching agents: []

			  .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			  '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::                (v3.2.5)

			2026-09-14T19:59:03.864-04:00  INFO 131007 --- [           main] com.dprev.checkout.MainKt                : Starting MainKt using Java 21.0.11 with PID 131007 (/home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot/target/classes started by root in /home/userlnx/docker/script_docker/java-ia/solid_hybrid/04-kotlin-spring-boot)
			2026-09-14T19:59:03.867-04:00  INFO 131007 --- [           main] com.dprev.checkout.MainKt                : No active profile set, falling back to 1 default profile: "default"
			2026-09-14T19:59:04.829-04:00  INFO 131007 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
			2026-09-14T19:59:04.845-04:00  INFO 131007 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
			2026-09-14T19:59:04.846-04:00  INFO 131007 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
			2026-09-14T19:59:04.899-04:00  INFO 131007 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
			2026-09-14T19:59:04.900-04:00  INFO 131007 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 970 ms
			2026-09-14T19:59:05.017-04:00  INFO 131007 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
			2026-09-14T19:59:05.270-04:00  INFO 131007 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
			2026-09-14T19:59:05.280-04:00  INFO 131007 --- [           main] com.dprev.checkout.MainKt                : Started MainKt in 1.828 seconds (process running for 2.17)

			=== TESTANDO ESTRUTURA ORIGINAL VIA SPRING ===
			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 100.0
			E-mail enviado para fabiano@email.com confirmando os R$ 100.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Cartão: Transacionando R$ 250.5 na operadora.
			E-mail enviado para fabiano@email.com confirmando os R$ 250.5
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Boleto: Gerando linha digitável para o valor de R$ 50.0
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			=== TESTANDO CONCEITOS DE CASCATA (EXTENDS & IMPLEMENTS) ===
			--- Iniciando Checkout ---
			Cartão Base: Processando R$  5000.0  na bandeira VISA
			E-mail enviado para diretoria@dprev.com confirmando os R$ 5000.0
			--- Checkout Finalizado ---

			Verificando rastreabilidade do cartão corporativo:
			[LOG AUDITORIA] Transação registrada no valor de R$ 5000.0
			Corporativo Premium: Convertendo taxa para USD
			Corporativo Premium: Estornando teto de R$ 5000.0 com prioridade.

			=== TESTANDO VIOLAÇÃO DO LSP (ESTUDO DE CASO) ===
			--- Iniciando Checkout ---
			VR: Processando R$ 50.0 via cartao beneficio.
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			[ALERTA ERRO] LSP Quebrado sob análise: Limite de transacao VR excedido!
			O código cliente não deve ser forçado a prever regras restritivas exclusivas de um subtipo.
			2026-09-14T19:59:08.653-04:00  INFO 131007 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
			2026-09-14T19:59:08.653-04:00  INFO 131007 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
			2026-09-14T19:59:08.654-04:00  INFO 131007 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms


│   ├── 📁 05-kotlin-puro/
			🧹 Parando todos os containers Docker da máquina...
			742eba164ff1
			69d24b27e8b5
			d09daec977be
			🧹 Encerrando instâncias antigas da aplicação...
			🐳 Subindo container PostgreSQL...
			🐳 Subindo container Redis...
			🐳 Subindo container LocalStack (SQS)...
			⏳ Aguardando os serviços inicializarem completamente...
			✅ PostgreSQL está pronto!
			✅ Redis está pronto!
			⏳ Aguardando LocalStack responder...
			✅ LocalStack (AWS SQS) está pronto!
			📦 Fila 'fila-notificacao-checkout' provisionada com sucesso no LocalStack.
			🚀 Compilando código Kotlin de Produção...
			🚀 Compilando código Kotlin com a versão 1.9.22...
			🏃 Executando a aplicação Kotlin Puro...
			🚀 Servidor Kotlin Puro rodando em http://localhost:8080
			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 100.0
			E-mail enviado para fabiano@email.com confirmando os R$ 100.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Cartão: Transacionando R$ 250.5 na operadora.
			E-mail enviado para fabiano@email.com confirmando os R$ 250.5
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			Boleto: Gerando linha digitável para o valor de R$ 50.0
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			=== TESTANDO CONCEITOS DE CASCATA (EXTENDS & IMPLEMENTS) ===
			--- Iniciando Checkout ---
			Cartão Base: Processando R$ 5000.0 na bandeira VISA
			E-mail enviado para diretoria@dprev.com confirmando os R$ 5000.0
			--- Checkout Finalizado ---

			[LOG AUDITORIA] Transação registrada no valor de R$ 5000.0
			Corporativo Premium: Convertendo taxa para USD
			Corporativo Premium: Estornando teto de R$ 5000.0 com prioridade.

			=== TESTANDO VIOLAÇÃO DO LSP (ESTUDO DE CASO) ===
			--- Iniciando Checkout ---
			VR: Processando R$ 50.0 via cartao beneficio.
			E-mail enviado para fabiano@email.com confirmando os R$ 50.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			[ALERTA ERRO] LSP Quebrado sob análise: Limite de transacao VR excedido!
			🧪 Compilando e Executando Testes de Integração em Kotlin...
			✅ Testes executados via infraestrutura.
			🚀 Aplicação rodando! Pressione CTRL+C para encerrar.
			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 250.0
			E-mail enviado para fabiano@email.com confirmando os R$ 250.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 250.0
			E-mail enviado para fabiano@email.com confirmando os R$ 250.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 250.0
			E-mail enviado para fabiano@email.com confirmando os R$ 250.0
			--- Checkout Finalizado ---

			--- Iniciando Checkout ---
			PIX: Gerando QR Code no valor de R$ 250.04
			E-mail enviado para fabiano@email.com confirmando os R$ 250.04
			--- Checkout Finalizado ---

│   ├── 📁 06-kafka-event-driven/

```

----------------------------------------------------------------

# Resposta do Agente:
Como Engenheiro de Software Java Sênior e Arquiteto de Sistemas, analisei a estrutura fornecida com foco em Clean Code, SOLID e padrões de projeto para identificar pontos de acoplamento excessivo e propor melhorias de desacoplamento, mantendo a leveza do ecossistema.

---

### Análise e Problemas Identificados

1.  **Acoplamento por Herança em Auditoria (`CartaoBase` com `ObjetoAuditavel`):**
    *   **Problema:** `CartaoBase extends ObjetoAuditavel`. A herança para responsabilidades transversais como auditoria cria um acoplamento rígido. Nem todo método de pagamento é auditável no mesmo contexto ou da mesma forma, e `CartaoBase` é obrigado a carregar a responsabilidade de auditoria, violando o Single Responsibility Principle (SRP) e o Interface Segregation Principle (ISP) de forma implícita. Isso torna difícil mudar a estratégia de auditoria sem afetar a hierarquia de pagamentos.
2.  **Parâmetros Primitivos Excessivos e Falta de Objeto de Domínio (`PagamentoRepository.salvar`, `CheckoutService.finalizarPedido`):**
    *   **Problema:** Métodos como `salvar` no `PagamentoRepository` e `finalizarPedido` no `CheckoutService` recebem múltiplos parâmetros primitivos (`double valor`, `String emailCliente`, `String chaveIdempotencia`, `MetodoPagamento formaDePagamento`). Essa "feature envy" e "data clump" sinaliza a ausência de um objeto de domínio ou Data Transfer Object (DTO) que encapsule os dados de uma transação de pagamento. Isso torna as assinaturas de métodos longas, difíceis de ler, propensas a erros de ordenação e frágeis a mudanças (violando o Open/Closed Principle - OCP). O repositório está persistindo detalhes avulsos e um `MetodoPagamento`, em vez de uma entidade coesa de `PaymentTransaction`.
3.  **Ambiguidade na Estrutura de Notificação (`NotificadorService` vs. `Notificador`):**
    *   **Problema:** Existe uma interface `Notificador` e suas implementações (`EmailNotificadorService`, `SqsNotificadorService`), o que é adequado. No entanto, a classe `NotificadorService` não implementa a interface `Notificador` e possui um método `enviarComprovante` com a mesma assinatura. Isso sugere redundância ou uma intenção de design (como Composite ou Facade) mal expressa, gerando confusão e potencial duplicação de lógica ou uso incorreto de dependências.

---

### Sugestões de Melhoria de Desacoplamento

### 1. Desacoplar Auditoria de `CartaoBase` (Usando Composição e Injeção de Dependência)

**Motivação:** Tratar a auditoria como um serviço separado, injetável, em vez de uma responsabilidade herdada. Isso adere ao SRP e ao Dependency Inversion Principle (DIP).

**Refatoração:**

*   Transformar `ObjetoAuditavel` em um serviço (`AuditoriaService`) com responsabilidade única de registrar eventos.
*   Remover a herança de `ObjetoAuditavel` de `CartaoBase`.
*   Injetar `AuditoriaService` nas classes que orquestram ações e precisam de auditoria (e.g., `CheckoutService`).

**Código Sugerido:**

```java
// --- ARQUIVO: AuditoriaService.java ---
// Antigo ObjetoAuditavel, refatorado para um serviço
public class AuditoriaService {
    public void registrarAuditoria(String evento, double valor, String usuario) {
        // Implementação real de log, persistência ou envio para sistema de auditoria
        System.out.println("[AUDITORIA] Evento: " + evento + " | Valor: " + valor + " | Usuário: " + usuario);
    }
}

// --- ARQUIVO: CartaoBase.java ---
// Remove a herança. CartaoBase agora foca apenas em ser um método de pagamento.
public abstract class CartaoBase implements MetodoPagamento {
    // Métodos e atributos comuns a todos os cartões
    // ...
}

// O uso do AuditoriaService será no CheckoutService, onde a orquestração acontece.
```

**Explicação:** A auditoria agora é uma preocupação transversal que pode ser injetada e utilizada onde for realmente necessária, sem forçar todas as subclasses de `CartaoBase` a implementá-la ou a serem afetadas por suas mudanças. Isso promove uma arquitetura mais flexível e aderente ao SRP.

### 2. Introduzir um Objeto de Transação de Pagamento (`PaymentTransaction`)

**Motivação:** Encapsular todos os dados relevantes de uma transação em um único objeto coeso. Isso simplifica as assinaturas de métodos, aumenta a coesão dos dados e adere ao SRP para objetos e métodos, além de facilitar a manutenção.

**Refatoração:**

*   Criar a classe `PaymentTransaction` para modelar uma transação de pagamento.
*   Atualizar `PagamentoRepository` e `CheckoutService` para operar com instâncias de `PaymentTransaction`.

**Código Sugerido:**

```java
// --- ARQUIVO: PaymentTransaction.java ---
public class PaymentTransaction {
    private Long id; // ID gerado pelo repositório
    private double valor;
    private String emailCliente;
    private String chaveIdempotencia;
    private MetodoPagamento metodoPagamento;
    private String status; // Ex: PENDING, PROCESSED, FAILED, REFUNDED
    private String destinoNotificacao; // Informação para onde enviar o comprovante

    public PaymentTransaction(double valor, String emailCliente, String chaveIdempotencia, MetodoPagamento metodoPagamento, String destinoNotificacao) {
        this.valor = valor;
        this.emailCliente = emailCliente;
        this.chaveIdempotencia = chaveIdempotencia;
        this.metodoPagamento = metodoPagamento;
        this.destinoNotificacao = destinoNotificacao;
        this.status = "PENDING"; // Estado inicial
    }

    // Getters para todos os atributos
    public Long getId() { return id; }
    public double getValor() { return valor; }
    public String getEmailCliente() { return emailCliente; }
    public String getChaveIdempotencia() { return chaveIdempotencia; }
    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }
    public String getStatus() { return status; }
    public String getDestinoNotificacao() { return destinoNotificacao; }

    // Setters apenas para atributos que podem ser alterados após a criação
    public void setId(Long id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }

    // Métodos de domínio para alterar o estado da transação
    public void markAsProcessed() { this.status = "PROCESSED"; }
    public void markAsFailed() { this.status = "FAILED"; }
    public void markAsRefunded() { this.status = "REFUNDED"; }
}

// --- ARQUIVO: PagamentoRepository.java ---
public interface PagamentoRepository {
    // Agora o método salvar recebe um objeto coeso, em vez de múltiplos primitivos
    void salvar(PaymentTransaction transaction);
    PaymentTransaction buscarPorId(Long id);
    List<PaymentTransaction> listarTodos();
}

// --- ARQUIVO: PagamentoJdbcRepository.java (Exemplo de Implementação Atualizada) ---
public class PagamentoJdbcRepository implements PagamentoRepository {
    private Connection getConnection() throws Exception { /* ... */ return null; }

    @Override
    public void salvar(PaymentTransaction transaction) {
        // Exemplo: Salva no banco de dados
        System.out.println("Persistindo transação ID: " + transaction.getId() + ", Status: " + transaction.getStatus());
        if (transaction.getId() == null) {
            transaction.setId(System.nanoTime()); // Simulação de geração de ID
        }
        // ... Lógica SQL para INSERT ou UPDATE usando todos os campos de 'transaction'
    }
    @Override
    public PaymentTransaction buscarPorId(Long id) { /* ... */ return null; }
    @Override
    public List<PaymentTransaction> listarTodos() { /* ... */ return null; }
}

// --- ARQUIVO: CheckoutService.java ---
public class CheckoutService {
    private final Notificador notificador;
    private final PagamentoRepository pagamentoRepository;
    private final AuditoriaService auditoriaService; // Adicionando a dependência do serviço de auditoria

    public CheckoutService(Notificador notificador, PagamentoRepository pagamentoRepository, AuditoriaService auditoriaService) {
        this.notificador = notificador;
        this.pagamentoRepository = pagamentoRepository;
        this.auditoriaService = auditoriaService;
    }

    // O método agora recebe um objeto de transação completo
    public void finalizarPedido(PaymentTransaction transaction) {
        try {
            // 1. Processar o pagamento
            transaction.getMetodoPagamento().processar(transaction.getValor());

            // 2. Salvar a transação (ou atualizar seu estado) no repositório
            pagamentoRepository.salvar(transaction);
            transaction.markAsProcessed(); // Atualiza o status no objeto em memória

            // 3. Notificar o cliente
            notificador.enviarComprovante(transaction.getDestinoNotificacao(), transaction.getValor());

            // 4. Registrar auditoria do evento
            auditoriaService.registrarAuditoria("Pagamento Processado com Sucesso", transaction.getValor(), transaction.getEmailCliente());

            System.out.println("Pedido finalizado com sucesso para: " + transaction.getEmailCliente() + " (Transação ID: " + transaction.getId() + ")");

        } catch (Exception e) {
            transaction.markAsFailed(); // Marca a transação como falha
            // Atualiza o repositório com o novo status
            pagamentoRepository.salvar(transaction);
            auditoriaService.registrarAuditoria("Falha no Processamento de Pagamento", transaction.getValor(), transaction.getEmailCliente() + " - Erro: " + e.getMessage());
            System.err.println("Erro ao finalizar pedido para " + transaction.getEmailCliente() + ": " + e.getMessage());
            throw new RuntimeException("Falha ao processar pedido", e); // Re-lançar exceção mais específica
        }
    }
}
```

**Explicação:** O `PaymentTransaction` atua como um objeto de domínio coeso, reduzindo drasticamente o acoplamento de classes às assinaturas de métodos. Isso melhora a legibilidade, facilita a adição de novos atributos à transação e centraliza a lógica de negócio em torno da entidade `PaymentTransaction`, promovendo o SRP e OCP.

### 3. Eliminar `NotificadorService` Redundante e Ajustar Injeção de Dependência

**Motivação:** Clarificar a responsabilidade da camada de notificação, garantindo que os clientes dependam apenas da interface `Notificador`, seguindo o DIP.

**Refatoração:**

*   Remover a classe `NotificadorService` se ela for de fato redundante.
*   Garantir que os consumidores de notificação (e.g., `CheckoutService`) recebam uma implementação da interface `Notificador`.

**Código Sugerido:**

```java
// --- ARQUIVO: NotificadorService.java (REMOVER) ---
// Esta classe é redundante se as implementações já existirem (EmailNotificadorService, SqsNotificadorService).
// Se a intenção era um "Composite" para enviar a vários notificadores, ele deveria implementar Notificador.

/*
// Exemplo de como seria um CompositeNotificadorService, caso essa fosse a intenção:
// --- ARQUIVO: CompositeNotificadorService.java ---
public class CompositeNotificadorService implements Notificador {
    private final List<Notificador> notificadores;

    public CompositeNotificadorService(List<Notificador> notificadores) {
        this.notificadores = Objects.requireNonNull(notificadores);
    }

    @Override
    public void enviarComprovante(String email, double valor) {
        notificadores.forEach(n -> n.enviarComprovante(email, valor));
    }
}
*/

// --- ARQUIVO: Main.java (Exemplo de Composição de Dependências) ---
// A classe Main (ou um contêiner IoC leve) orquestra a criação e injeção das dependências.
public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Instanciar Repositórios de Pagamento
        PagamentoRepository postgresRepository = new PagamentoPostgresRepository();
        IdempotencyKeyGenerator keyGenerator = new IdempotencyKeyGenerator();
        // Aplicando o padrão Decorator para Idempotência
        PagamentoRepository finalPagamentoRepository = new PagamentoIdempotenteRedisRepository(postgresRepository, keyGenerator);

        // 2. Instanciar Serviços de Notificação
        Notificador emailNotificador = new EmailNotificadorService();
        Notificador sqsNotificador = new SqsNotificadorService();
        // Escolher qual notificador usar, ou um Composite
        Notificador chosenNotificador = emailNotificador; // Ou new CompositeNotificadorService(Arrays.asList(emailNotificador, sqsNotificador));

        // 3. Instanciar Serviço de Auditoria
        AuditoriaService auditoriaService = new AuditoriaService();

        // 4. Montar o CheckoutService com as dependências
        CheckoutService checkoutService = new CheckoutService(chosenNotificador, finalPagamentoRepository, auditoriaService);

        // 5. Inicializar o WebServer com o CheckoutService
        WebServer webServer = new WebServer(checkoutService);
        webServer.iniciar(8080);

        // Exemplo de uso
        // MetodoPagamento cartao = new CartaoCreditoPagamento();
        // PaymentTransaction transaction = new PaymentTransaction(150.75, "cliente@email.com", keyGenerator.gerarChaveAleatoria(), cartao, "cliente@email.com");
        // checkoutService.finalizarPedido(transaction);
    }
}
```

**Explicação:** A remoção do `NotificadorService` redundante simplifica a arquitetura e evita ambiguidades. O `CheckoutService` agora depende explicitamente da interface `Notificador`, que pode ser injetada com qualquer implementação concreta ou um `CompositeNotificadorService`, sem que `CheckoutService` precise ter conhecimento dos detalhes de implementação. Isso reforça o DIP e o OCP.

---

### Boas Práticas Corporativas Adicionais (Sem Frameworks Pesados)

1.  **Manipulação de Classpath Manual:** Sua solicitação menciona "falhas em manipulação de classpath manual". **Recomendação Urgente:** Abandone a manipulação manual. Adote uma ferramenta de build como **Maven** ou **Gradle**. Elas gerenciam dependências, classpath, compilação e empacotamento de forma padronizada, eliminando erros, garantindo builds repetíveis e facilitando a integração contínua.
2.  **Injeção de Dependência Leve (Manual ou Guice):** Sem Spring, a injeção manual de dependências no ponto de entrada (`Main`) é uma prática excelente para entender e controlar o grafo de objetos. Para projetos maiores, mas ainda leves, o **Google Guice** pode ser considerado como um contêiner IoC minimalista, oferecendo os benefícios da injeção de dependência sem a sobrecarga do ecossistema Spring.
3.  **Padrão Decorator:** O uso do `PagamentoIdempotenteRedisRepository` como um decorator para `PagamentoRepository` é um excelente exemplo. Continue aplicando este padrão para adicionar responsabilidades transversais (como caching, logging, autorização) de forma modular, sem modificar as classes de domínio ou serviços existentes.
4.  **Logging Adequado:** Substitua `System.out.println` por uma biblioteca de logging robusta como **SLF4J** (interface) com **Logback** ou **Log4j2** (implementação). Isso permite configurar níveis de log, destinos (arquivo, console, sistemas de monitoramento) e formatos, essencial para depuração e monitoramento em produção.
5.  **Exceções de Domínio:** Crie uma hierarquia de exceções customizadas que representem falhas específicas do domínio da aplicação (e.g., `PaymentProcessingException`, `InvalidPaymentMethodException`). Isso torna o tratamento de erros mais claro e semântico do que apenas lançar `RuntimeException`.
6.  **Interfaces Pequenas e Coesas (ISP):** Mantenha interfaces como `Estornavel`, `MetodoPagamento`, `Notificador` com responsabilidades bem definidas. Isso permite que classes implementem apenas o que realmente precisam, aderindo ao Interface Segregation Principle.

Estas melhorias visam construir um sistema mais robusto, flexível e manutenível, alinhado com os princípios de Clean Code e SOLID, sem introduzir dependências ou complexidades desnecessárias de frameworks pesados.
[Finished in 46.1s]




📁 02-java-spring-boot/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 com/
│   │   │       └── 📁 dprev/
│   │   │           ├── 📄 Main.java                 # Bootstrapper Spring Boot
│   │   │           │
│   │   │           ├── 📁 core/                     # Infraestrutura transversal e configs globais
│   │   │           │   ├── 📁 config/
│   │   │           │   │   └── 📄 CheckoutConfig.java
│   │   │           │   └── 📁 exception/
│   │   │           │       └── 📄 GlobalExceptionHandler.java
│   │   │           │
│   │   │           └── 📁 modules/                  # Domínios isolados (Bounded Contexts)
│   │   │               └── 📁 pagamento/            # Módulo de Pagamento/Checkout
│   │   │                   ├── 📁 controller/
│   │   │                   │   └── 📄 CheckoutController.java
│   │   │                   ├── 📁 domain/           # Entidades JPA, Enums e Strategies
│   │   │                   │   ├── 📄 Estornavel.java
│   │   │                   │   ├── 📄 MetodoPagamento.java
│   │   │                   │   ├── 📄 PagamentoInternacional.java
│   │   │                   │   ├── 📁 base/
│   │   │                   │   │   ├── 📄 CartaoBase.java
│   │   │                   │   │   └── 📄 ObjetoAuditavel.java
│   │   │                   │   ├── 📁 cascade/
│   │   │                   │   │   └── 📄 CartaoCorporativoPremium.java
│   │   │                   │   └── 📁 strategy/
│   │   │                   │       ├── 📄 BoletoPagamento.java
│   │   │                   │       ├── 📄 CartaoCreditoPagamento.java
│   │   │                   │       ├── 📄 PixPagamento.java
│   │   │                   │       └── 📄 ValeRefeicaoPagamento.java
│   │   │                   ├── 📁 notification/     # Notificadores (E-mail, SQS)
│   │   │                   │   ├── 📄 EmailNotificadorService.java
│   │   │                   │   ├── 📄 Notificador.java
│   │   │                   │   └── 📄 SqsNotificadorService.java
│   │   │                   ├── 📁 repository/       # Spring Data JPA + Decorator Redis
│   │   │                   │   ├── 📄 PagamentoArquivoRepository.java
│   │   │                   │   ├── 📄 PagamentoIdempotenteRedisRepository.java
│   │   │                   │   ├── 📄 PagamentoPostgresRepository.java
│   │   │                   │   ├── 📄 PagamentoRepository.java
│   │   │                   │   ├── 📄 PagamentoSqliteRepository.java
│   │   │                   │   └── 📄 SpringDataPagamentoRepository.java
│   │   │                   └── 📁 service/          # Casos de uso e utilitários
│   │   │                       ├── 📄 CheckoutService.java
│   │   │                       └── 📄 IdempotencyKeyGenerator.java
│   │   │
│   │   └── 📁 resources/
│   │       └── 📄 application.properties
│   │
│   └── 📁 test/
│       └── 📁 java/
│           └── 📁 com/
│               └── 📁 dprev/
│                   └── 📁 checkout/
│                       └── 📄 CheckoutSOLIDTest.java
├── 📄 pom.xml
├── 📄 reset.sh
└── 📄 up.sh


01-java-puro/
└── src/
    └── main/
        └── java/
            └── com/
                └── dprev/
                    └── checkout/
                        ├── core/
                        │   └── config/
                        │       └── AppConfig.java
                        └── modules/
                            └── pagamento/
                                ├── domain/
                                │   ├── MetodoPagamento.java
                                │   ├── Estornavel.java
                                │   ├── PagamentoInternacional.java
                                │   ├── base/
                                │   │   ├── ObjetoAuditavel.java
                                │   │   └── CartaoBase.java
                                │   ├── cascade/
                                │   │   └── CartaoCorporativoPremium.java
                                │   └── strategy/
                                │       ├── PixPagamento.java
                                │       ├── CartaoCreditoPagamento.java
                                │       ├── BoletoPagamento.java
                                │       └── ValeRefeicaoPagamento.java
                                │   (Demais classes de service, repository e notification do puro...)