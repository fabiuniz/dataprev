#!/bin/bash
clear

# =====================================================================
# 0. Limpeza total de containers Docker e instâncias
# =====================================================================
echo "🧹 Parando todos os containers Docker da máquina..."
docker stop $(docker ps -aq) 2>/dev/null || true
echo "🗑️ Removendo containers antigos para evitar conflitos..."
docker rm $(docker ps -aq) 2>/dev/null || true
echo "🧹 Encerrando instâncias antigas da aplicação..."
lsof -t -i:8080 > /dev/null 2>&1 && kill -9 $(lsof -t -i:8080) 2>/dev/null || true
pkill -f "spring-boot:run" 2>/dev/null || true

# =====================================================================
# 1. Configurações e Variáveis de Infraestrutura
# =====================================================================
PG_CONTAINER="pg-checkout"
REDIS_CONTAINER="redis-checkout"
LOCALSTACK_CONTAINER="localstack-checkout"

REDIS_PASS="SuaSenhaSuperSegura123"

echo "🐳 Verificando infraestrutura em containers para a Task 03 (Java + Kotlin + Spring Boot)..."

# --- Postgres ---
if [ "$(docker ps -aq -f name=${PG_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${PG_CONTAINER})" ]; then
        echo "🔄 Postgres já está rodando."
    else
        echo "▶️ Iniciando Postgres..."
        docker start ${PG_CONTAINER} > /dev/null
    fi
else
    echo "🚀 Criando container PostgreSQL..."
    docker run --name ${PG_CONTAINER} \
      -e POSTGRES_USER=postgres \
      -e POSTGRES_PASSWORD=1234 \
      -e POSTGRES_DB=checkout_db \
      -p 5432:5432 \
      -d postgres:15-alpine > /dev/null
fi

# --- Redis ---
if [ "$(docker ps -aq -f name=${REDIS_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${REDIS_CONTAINER})" ]; then
        echo "🔄 Redis já está rodando."
    else
        echo "▶️ Iniciando Redis..."
        docker start ${REDIS_CONTAINER} > /dev/null
    fi
else
    echo "🚀 Criando container Redis..."
    docker run --name ${REDIS_CONTAINER} \
      -p 6379:6379 \
      -d redis:7-alpine redis-server --requirepass "$REDIS_PASS" > /dev/null
fi

# --- LocalStack (AWS SQS Local) ---
if [ "$(docker ps -aq -f name=${LOCALSTACK_CONTAINER})" ]; then
    if [ "$(docker ps -q -f name=${LOCALSTACK_CONTAINER})" ]; then
        echo "🔄 LocalStack já está rodando."
    else
        echo "▶️ Iniciando LocalStack..."
        docker start ${LOCALSTACK_CONTAINER} > /dev/null
    fi
else
    echo "☁️ Criando container LocalStack (SQS)..."
    docker run --name ${LOCALSTACK_CONTAINER} \
      -p 4566:4566 \
      -e SERVICES=sqs \
      -d localstack/localstack:3.0.2 > /dev/null
fi

# =====================================================================
# 2. Validação de Prontidão dos Serviços Externalizados
# =====================================================================
echo "⏳ Aguardando o PostgreSQL ficar pronto..."
until docker exec ${PG_CONTAINER} pg_isready -U postgres > /dev/null 2>&1; do sleep 0.5; done
echo "✅ PostgreSQL está pronto!"

echo "⏳ Aguardando o Redis responder..."
until [ "$(docker exec ${REDIS_CONTAINER} redis-cli -a "$REDIS_PASS" ping 2>/dev/null | tr -d '\r')" = "PONG" ]; do sleep 0.5; done
echo "✅ Redis está pronto!"

echo "⏳ Aguardando LocalStack responder..."
until [ "$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4566/_localstack/health)" = "200" ]; do sleep 0.5; done
echo "✅ LocalStack (AWS SQS) está pronto!"

# Provisionamento da Fila no SQS local
docker exec ${LOCALSTACK_CONTAINER} awslocal sqs create-queue --queue-name fila-notificacao-checkout > /dev/null 2>&1
echo "📦 Fila 'fila-notificacao-checkout' provisionada no LocalStack."

# =====================================================================
# 3. Execução do Ecossistema Spring Boot (Java + Kotlin)
# =====================================================================
echo "🏃 Compilando e executando o ecossistema Spring Boot via Maven..."
mvn clean spring-boot:run