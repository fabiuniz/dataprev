#!/bin/bash

# 1. Verifica se o kotlinc existe. Se não existir, avisa o usuário
if ! command -v kotlinc &> /dev/null; then
    echo "⚠️  Compilador 'kotlinc' não encontrado."
    echo "Por favor, instale executando: sudo apt update && sudo apt install kotlin -y"
    exit 1
fi

echo "🧹 Limpando resíduos antigos..."
rm -f checkout-solid.jar

echo "🚀 Compilando o projeto com kotlinc..."
# O 'find' alimenta o 'kotlinc' com a lista exata de arquivos, garantindo que o Main.kt seja compilado
find src/main/kotlin -name "*.kt" | xargs kotlinc -include-runtime -d checkout-solid.jar

echo "🏃 Executando a aplicação nativa..."
# Como o kotlinc agora achou o Main.kt, ele injeta o ponto de partida automaticamente no JAR!
java -jar checkout-solid.jar