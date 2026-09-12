# =====================================================================
# 0. Limpeza total de containers Docker e instâncias
# =====================================================================
echo "🧹 Parando todos os containers Docker da máquina..."
docker stop $(docker ps -aq) 2>/dev/null || true
echo "🗑️ Removendo containers antigos para evitar conflitos..."
docker rm $(docker ps -aq) 2>/dev/null || true

mvn spring-boot:run
