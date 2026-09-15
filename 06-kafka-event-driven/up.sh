#!/bin/bash
clear

# Caminho absoluto da raiz do Módulo 06
MODULE_PATH="/home/userlnx/docker/script_docker/java-ia/solid_hybrid/06-kafka-event-driven"

# =====================================================================
# 📊 ARTEFATOS E SERVIÇOS LOCAIS DO MÓDULO 06 (CLICÁVEIS)
# =====================================================================

echo "🔗 Links e Artefatos do Ecossistema (Ctrl + Clique):"
echo -e " • \e]8;;file://$MODULE_PATH/target/site/jacoco/index.html\e\\Relatório JaCoCo (Cobertura de Testes)\e]8;;\e\\"
echo -e " • \e]8;;http://vmlinuxd:9092\e\\Kafka Broker (Mensageria)\e]8;;\e\\"
echo -e " • \e]8;;http://vmlinuxd:5432\e\\PostgreSQL (Banco Relacional)\e]8;;\e\\"
echo -e " • \e]8;;http://vmlinuxd:6379\e\\Redis (Cache / Idempotência)\e]8;;\e\\"
echo -e " • \e]8;;http://vmlinuxd:4566\e\\LocalStack (Simulador AWS SQS)\e]8;;\e\\"
echo "---------------------------------------------------------------------"

echo "🐳 Subindo infraestrutura do Kafka e Zookeeper..."
docker compose up -d
echo "✅ Kafka e Zookeeper iniciados com sucesso!"
echo "---------------------------------------------------------------------"

echo "☕ Executando pipeline Maven (Build, Testes e Quality Gate JaCoCo)..."
cd "$MODULE_PATH" || exit 1
mvn clean verify

echo "---------------------------------------------------------------------"
echo "🎉 Processo concluído! O relatório do JaCoCo foi gerado com sucesso."

# Opcional: Subir um servidor web local em background para a porta 8000
# (Assim você pode acessar http://vmlinuxd:8000 de forma interativa)
if [ -d "target/site/jacoco" ]; then
    # Mata eventual servidor anterior rodando na porta 8000
    fuser -k 8000/tcp &> /dev/null
    
    cd target/site/jacoco || exit 1
    python3 -m http.server 8000 &> /dev/null &
    
    echo "🌐 Servidor HTTP do JaCoCo ativo em background!"
    echo -e "👉 Acesse via navegador: \e]8;;http://vmlinuxd:8000\e\\http://vmlinuxd:8000\e]8;;\e\\"
fi