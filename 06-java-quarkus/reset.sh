#!/usr/bin/env bash
# Limpa APENAS este módulo: containers do compose + target/. Nunca apaga código-fonte.
cd "$(dirname "$0")"
docker compose down 2>/dev/null || true
mvn -q clean 2>/dev/null || rm -rf target
echo "✅ Módulo 06 limpo (containers do compose parados, target/ removido)."
