#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

apt update && apt install maven

echo "🐳 Subindo Postgres + Redis (com senha) + LocalStack (SQS)..."
docker compose up -d --wait
echo "✅ Infra pronta."


echo
echo "🔗 Links (Ctrl + Clique):"
echo "   • App:        http://vmlinux-usr:8080"
echo "   • Swagger UI: http://vmlinux-usr:8080/q/swagger-ui"
echo "   • Dev UI:     http://vmlinux-usr:8080/q/dev-ui"
echo "   • JaCoCo:     target/jacoco-report/index.html (após mvn verify)"
echo

mvn quarkus:dev -Dquarkus.http.host=0.0.0.0
