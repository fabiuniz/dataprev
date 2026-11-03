#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

PORTA=8080

echo "🧹 Liberando a porta ${PORTA}..."
pkill -f checkout-helidon.jar 2>/dev/null || true
sleep 2

PID=$(ss -ltnp 2>/dev/null | grep ":${PORTA} " | sed -n 's/.*pid=\([0-9]*\).*/\1/p' | head -1)
if [ -n "$PID" ]; then
  echo "⚠️  Porta ${PORTA} ainda ocupada pelo PID ${PID}. Tentando encerrar..."
  kill "$PID" 2>/dev/null || true
  sleep 2
  kill -9 "$PID" 2>/dev/null || true
  sleep 1
fi

if ss -ltn 2>/dev/null | grep -q ":${PORTA} "; then
  echo "❌ Não consegui liberar a porta ${PORTA} (processo de outro usuário?)."
  echo "   Rode este script como root, ou: sudo kill -9 <PID>  (veja: ss -ltnp | grep ${PORTA})"
  exit 1
fi
echo "✅ Porta ${PORTA} livre."

echo "🐳 Subindo Postgres + Redis + LocalStack para o Helidon..."
docker compose up -d --wait
echo "✅ Infra pronta."

echo "🚀 Compilando e empacotando aplicação Helidon MP..."
mvn clean package -DskipTests

echo
echo "🔗 Links (Ctrl + Clique):"
echo "   • App:     http://vmlinux-usr:${PORTA}/api/status"
echo "   • Health:  http://vmlinux-usr:${PORTA}/observe/health"
echo "   • OpenAPI: http://vmlinux-usr:${PORTA}/openapi"
echo "   • Metrics: http://vmlinux-usr:${PORTA}/observe/metrics"
echo

echo "🚀 Iniciando aplicação Helidon MP na porta ${PORTA}..."
exec java -jar target/checkout-helidon.jar