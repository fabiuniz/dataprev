#!/bin/bash
clear

# =====================================================================
# 0. Limpeza de instâncias anteriores (Evita Address already in use)
# =====================================================================
echo "🧹 Parando todos os containers Docker da máquina..."
docker stop $(docker ps -q) 2>/dev/null || true
echo "🧹 Encerrando instâncias antigas da aplicação..."
pkill -f com.tqi.checkout.Main 2>/dev/null || true
# =====================================================================
# 1. Configurações e Variáveis
# =====================================================================
PG_CONTAINER="pg-checkout"
REDIS_CONTAINER="redis-checkout"
LOCALSTACK_CONTAINER="localstack-checkout"

FOLDER_JAR="../jars"
mkdir -p "$FOLDER_JAR"
DRIVER_JAR="$FOLDER_JAR/postgresql-42.7.3.jar"
JEDIS_JAR="$FOLDER_JAR/jedis-4.4.3.jar"
POOL_JAR="$FOLDER_JAR/commons-pool2-2.11.1.jar"

# 🟢 CORREÇÃO 1: Adicionando a variável e o JAR Bundle da AWS (Traz todas as dependências internas junto)
AWS_SDK_JAR="$FOLDER_JAR/aws-java-sdk-bundle-1.12.500.jar"

REDIS_PASS="SuaSenhaSuperSegura123"
REDIS_USER="default"

# =====================================================================
# 2. Downloads das Dependências (Se não existirem)
# =====================================================================
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
    echo "📥 Baixando AWS Java SDK Bundle (isso pode demorar um pouco, ~160MB)..."
    curl -L -sS -o "$AWS_SDK_JAR" https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.12.500/aws-java-sdk-bundle-1.12.500.jar
fi

# --- Gerenciamento do POSTGRES ---
if [ "$(docker ps -aq -f name=${PG_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${PG_CONTAINER})" ]; then
        echo "🔄 Container $PG_CONTAINER (Postgres) já está rodando."
    else
        echo "⏹️  Container $PG_CONTAINER existe mas está parado. Iniciando..."
        docker start ${PG_CONTAINER} > /dev/null
    fi
else
    echo "🐳 Criando e iniciando um novo container PostgreSQL..."
    docker run --name ${PG_CONTAINER} \
      -e POSTGRES_USER=postgres \
      -e POSTGRES_PASSWORD=1234 \
      -e POSTGRES_DB=checkout_db \
      -p 5432:5432 \
      -d postgres:15-alpine > /dev/null
fi

# --- Gerenciamento do REDIS ---
if [ "$(docker ps -aq -f name=${REDIS_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${REDIS_CONTAINER})" ]; then
        echo "🔄 Container $REDIS_CONTAINER (Redis) já está rodando."
    else
        echo "⏹️  Container $REDIS_CONTAINER existe mas está parado. Iniciando..."
        docker start ${REDIS_CONTAINER} > /dev/null
    fi
else
    echo "🔴 Criando e iniciando um novo container Redis COM SENHA..."
    docker run --name ${REDIS_CONTAINER} \
      -p 6379:6379 \
      -d redis:7-alpine redis-server --requirepass "$REDIS_PASS" > /dev/null
fi

# --- 🟢 Gerenciamento do LOCALSTACK (AWS SQS Local) ---
if [ "$(docker ps -aq -f name=${LOCALSTACK_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${LOCALSTACK_CONTAINER})" ]; then
        echo "🔄 Container $LOCALSTACK_CONTAINER (LocalStack) já está rodando."
    else
        echo "⏹️  Container $LOCALSTACK_CONTAINER existe mas está parado. Iniciando..."
        docker start ${LOCALSTACK_CONTAINER} > /dev/null
    fi
else
    echo "☁️ Criando e iniciando um novo container LocalStack (SQS)..."
    docker run --name ${LOCALSTACK_CONTAINER} \
      -p 4566:4566 \
      -e SERVICES=sqs \
      -d localstack/localstack:3.0.2 > /dev/null
fi

# Validação do Ambiente Java
if ! command -v javac &> /dev/null; then
    echo "⚠️  Compilador 'javac' não encontrado."
    echo "Por favor, instale executando: sudo apt update && sudo apt install default-jdk -y"
    exit 1
fi

# =====================================================================
# 3. Ciclo de Build Limpo e Compilação Separada (Main e Test)
# =====================================================================
echo "🧹 Limpando resíduos antigos..."
rm -rf bin
rm -f $FOLDER_JAR/checkout-solid.jar
mkdir -p bin

echo "🚀 Compilando código de Produção (Main)..."
find src/main/java -name "*.java" > fontes_main.txt
# 🟢 Passando o Classpath unificado em string com as variáveis devidamente interpoladas
javac -cp ".:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR" -d bin @fontes_main.txt || exit 1
rm fontes_main.txt

echo "🧪 Compilando código de Testes (Test)..."
find src/test/java -name "*.java" > fontes_test.txt
javac -cp "bin:.:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR" -d bin @fontes_test.txt || exit 1
rm fontes_test.txt

echo "📦 Empacotando tudo em JAR Executável..."
jar cfe $FOLDER_JAR/checkout-solid.jar com.tqi.checkout.Main -C bin . || exit 1

# =====================================================================
# 4. Validação de Prontidão dos Serviços Externalizados
# =====================================================================
echo "⏳ Aguardando os serviços inicializarem completamente..."
until docker exec ${PG_CONTAINER} pg_isready -U postgres > /dev/null 2>&1; do sleep 0.5; done
echo "✅ PostgreSQL está pronto!"

until [ "$(docker exec ${REDIS_CONTAINER} redis-cli -a "$REDIS_PASS" ping 2>/dev/null | tr -d '\r')" = "PONG" ]; do sleep 0.5; done
echo "✅ Redis está pronto!"

echo "⏳ Aguardando LocalStack responder..."
until [ "$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4566/_localstack/health)" = "200" ]; do sleep 0.5; done
echo "✅ LocalStack (AWS SQS) está pronto!"

# Garantir a criação da fila no SQS local para o Java conseguir enviar dados imediatamente
docker exec ${LOCALSTACK_CONTAINER} awslocal sqs create-queue --queue-name fila-notificacao-checkout > /dev/null
echo "📦 Fila 'fila-notificacao-checkout' provisionada com sucesso no LocalStack."

# =====================================================================
# 5. Execução do Ecossistema (Unificado e Corrigido)
# =====================================================================
echo "🏃 Executando a aplicação nativa híbrida (Main)..."
java -cp "$FOLDER_JAR/checkout-solid.jar:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR" com.tqi.checkout.Main &
MAIN_PID=$!

# Pequena pausa para o WebServer interno iniciar
sleep 2

echo "🧪 Executando Testes de Integração (CheckoutSOLIDTest)..."
java -cp "$FOLDER_JAR/checkout-solid.jar:$DRIVER_JAR:$JEDIS_JAR:$POOL_JAR:$AWS_SDK_JAR" com.tqi.checkout.test.CheckoutSOLIDTest

# Mantém o processo principal visível
wait $MAIN_PID