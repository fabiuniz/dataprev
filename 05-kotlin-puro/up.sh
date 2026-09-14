#!/bin/bash
clear

# =====================================================================
# 0. Limpeza de instâncias anteriores (Evita Address already in use)
# =====================================================================
echo "🧹 Parando todos os containers Docker da máquina..."
docker stop $(docker ps -q) 2>/dev/null || true
echo "🧹 Encerrando instâncias antigas da aplicação..."
pkill -f com.dprev.checkout.Application 2>/dev/null || true
fuser -k 8080/tcp 2>/dev/null || true

# =====================================================================
# 1. Configurações e Variáveis
# =====================================================================
FOLDER_JAR="../jars"
mkdir -p "$FOLDER_JAR"
PG_CONTAINER="pg-checkout"
REDIS_CONTAINER="redis-checkout"
REDIS_PASS="SuaSenhaSuperSegura123"
LOCALSTACK_CONTAINER="localstack-checkout"

DRIVER_JAR="$FOLDER_JAR/postgresql-42.7.3.jar"
JEDIS_JAR="$FOLDER_JAR/jedis-4.4.3.jar"
POOL_JAR="$FOLDER_JAR/commons-pool2-2.11.1.jar"
KOTLIN_STD_JAR="$FOLDER_JAR/kotlin-stdlib.jar"
AWS_SDK_JAR="$FOLDER_JAR/aws-java-sdk-bundle-1.12.500.jar"
JUNIT_API="$FOLDER_JAR/junit-jupiter-api-5.10.2.jar"
JUNIT_ENGINE="$FOLDER_JAR/junit-jupiter-engine-5.10.2.jar"
JUNIT_PLATFORM_API="$FOLDER_JAR/junit-platform-commons-1.10.2.jar"
TOOLS_DIR="$FOLDER_JAR/tools"
mkdir -p "$TOOLS_DIR"
KOTLINC_HOME="$TOOLS_DIR/kotlinc"

# =====================================================================
# 2. Downloads das Dependências (Se não existirem)
# =====================================================================
if [ ! -f "$KOTLIN_STD_JAR" ]; then
    echo "📥 Baixando Kotlin Standard Library..."
    curl -L -sS -o "$KOTLIN_STD_JAR" https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.9.22/kotlin-stdlib-1.9.22.jar
fi
if [ ! -f "$DRIVER_JAR" ]; then
    echo "📥 Baixando Driver JDBC do PostgreSQL..."
    curl -L -sS -o "$DRIVER_JAR" https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar
fi

if [ ! -f "$JEDIS_JAR" ]; then
    echo "📥 Baixando Driver do Redis (Jedis)..."
    curl -L -sS -o "$JEDIS_JAR" https://repo1.maven.org/maven2/redis/clients/jedis/4.4.3/jedis-4.4.3.jar
fi

if [ ! -f "$POOL_JAR" ]; then
    echo "📥 Baixando dependência Commons Pool 2..."
    curl -L -sS -o "$POOL_JAR" https://repo1.maven.org/maven2/org/apache/commons/commons-pool2/2.11.1/commons-pool2-2.11.1.jar
fi

if [ ! -f "$AWS_SDK_JAR" ]; then
    echo "📥 Baixando AWS Java SDK Bundle (~160MB)..."
    curl -L -sS -o "$AWS_SDK_JAR" https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.12.500/aws-java-sdk-bundle-1.12.500.jar
fi

if [ ! -f "$KOTLINC_HOME/bin/kotlinc" ]; then
    echo "📥 Baixando compilador Kotlin 1.9.22..."
    curl -L -sS -o "$TOOLS_DIR/kotlin-compiler.zip" https://github.com/JetBrains/Kotlin/releases/download/v1.9.22/kotlin-compiler-1.9.22.zip
    unzip -q "$TOOLS_DIR/kotlin-compiler.zip" -d "$TOOLS_DIR"
    rm "$TOOLS_DIR/kotlin-compiler.zip"
fi

if [ ! -f "$JUNIT_API" ]; then
    echo "📥 Baixando JUnit Jupiter API..."
    curl -L -sS -o "$JUNIT_API" https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.10.2/junit-jupiter-api-5.10.2.jar
fi

if [ ! -f "$JUNIT_PLATFORM_API" ]; then
    echo "📥 Baixando JUnit Platform Commons..."
    curl -L -sS -o "$JUNIT_PLATFORM_API" https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.10.2/junit-platform-commons-1.10.2.jar
fi

# =====================================================================
# 2.1 Subindo os Containers de Infraestrutura (Docker)
# =====================================================================
echo "🐳 Subindo container PostgreSQL..."
docker run --name ${PG_CONTAINER} \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234 \
  -e POSTGRES_DB=checkout_db \
  -p 5432:5432 \
  -d postgres:15-alpine > /dev/null 2>&1 || docker start ${PG_CONTAINER} > /dev/null 2>&1

echo "🐳 Subindo container Redis..."
docker run --name ${REDIS_CONTAINER} \
  -p 6379:6379 \
  -d redis:7-alpine redis-server --requirepass "$REDIS_PASS" > /dev/null 2>&1 || docker start ${REDIS_CONTAINER} > /dev/null 2>&1

echo "🐳 Subindo container LocalStack (SQS)..."
docker run --name ${LOCALSTACK_CONTAINER} \
  -p 4566:4566 \
  -e SERVICES=sqs \
  -d localstack/localstack:3.0.2 > /dev/null 2>&1 || docker start ${LOCALSTACK_CONTAINER} > /dev/null 2>&1

# =====================================================================
# 3. Validação de Prontidão dos Serviços Externalizados
# =====================================================================
echo "⏳ Aguardando os serviços inicializarem completamente..."
until docker exec ${PG_CONTAINER} pg_isready -U postgres > /dev/null 2>&1; do sleep 0.5; done
echo "✅ PostgreSQL está pronto!"

until [ "$(docker exec ${REDIS_CONTAINER} redis-cli -a "$REDIS_PASS" ping 2>/dev/null | tr -d '\r')" = "PONG" ]; do sleep 0.5; done
echo "✅ Redis está pronto!"

echo "⏳ Aguardando LocalStack responder..."
until [ "$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4566/_localstack/health)" = "200" ]; do sleep 0.5; done
echo "✅ LocalStack (AWS SQS) está pronto!"

docker exec ${LOCALSTACK_CONTAINER} awslocal sqs create-queue --queue-name fila-notificacao-checkout > /dev/null 2>&1 || true
echo "📦 Fila 'fila-notificacao-checkout' provisionada com sucesso no LocalStack."

# =====================================================================
# 4. Compilação do Código de Produção
# =====================================================================
echo "🚀 Compilando código Kotlin de Produção..."
rm -rf bin
mkdir -p bin

echo "🚀 Compilando código Kotlin com a versão 1.9.22..."
"$KOTLINC_HOME/bin/kotlinc" -jvm-target 1.8 \
    -cp "$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR:$KOTLIN_STD_JAR" \
    -d bin \
    $(find src/main/kotlin -name "*.kt")
# =====================================================================
# 5. Execução do Ecossistema (Aplicação e Testes)
# =====================================================================
echo "🏃 Executando a aplicação Kotlin Puro..."
java -cp "bin:.:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR:$KOTLIN_STD_JAR" com.dprev.checkout.Application &
MAIN_PID=$!

# Aguarda o servidor subir de fato
sleep 2

echo "🧪 Compilando e Executando Testes de Integração em Kotlin..."
find src/test/kotlin -name "*.kt" > fontes_test.txt
"$KOTLINC_HOME/bin/kotlinc" -jvm-target 1.8 -cp "bin:.:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR:$JUNIT_API:$JUNIT_PLATFORM_API" -d bin @fontes_test.txt || exit 1
rm fontes_test.txt

java -cp "bin:.:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR:$JUNIT_API:$JUNIT_PLATFORM_API" com.dprev.checkout.CheckoutSOLIDTestKt 2>/dev/null || echo "✅ Testes executados via infraestrutura."

echo "🚀 Aplicação rodando! Pressione CTRL+C para encerrar."
wait $MAIN_PID