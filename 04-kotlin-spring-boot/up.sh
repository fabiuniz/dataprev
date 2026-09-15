#!/bin/bash
clear

echo "🔍 Liberando a porta 8080..."
# Mata qualquer processo escutando na porta 8080
fuser -k 8080/tcp 2>/dev/null || true
# Ou força a parada de qualquer processo Java remanescente se necessário
pkill -f java 2>/dev/null || true

# =====================================================================
# 0. Limpeza total de containers Docker e instâncias
# =====================================================================
echo "🧹 Parando todos os containers Docker da máquina..."
docker stop $(docker ps -aq) 2>/dev/null || true
echo "🗑️ Removendo containers antigos para evitar conflitos..."
docker rm $(docker ps -aq) 2>/dev/null || true

echo "🚀 Iniciando a aplicação Spring Boot..."
mvn spring-boot:run