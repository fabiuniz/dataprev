#!/usr/bin/env bash
cd "$(dirname "$0")"
docker compose down 2>/dev/null || true
mvn -q clean 2>/dev/null || rm -rf target
echo "✅ Módulo 07 (Helidon) limpo."