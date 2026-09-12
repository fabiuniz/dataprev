#!/bin/bash
clear

PG_CONTAINER="pg-checkout"
REDIS_CONTAINER="redis-checkout"
REDIS_PASS="SuaSenhaSuperSegura123"

echo "🐳 Verificando infraestrutura em containers..."

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

# --- Healthcheck ---
echo "⏳ Aguardando o banco de dados ficar pronto..."
until docker exec ${PG_CONTAINER} pg_isready -U postgres > /dev/null 2>&1; do sleep 0.5; done
echo "✅ Serviços prontos!"

# --- Execução do Spring Boot ---
echo "🏃 Executando Spring Boot..."
mvn spring-boot:run