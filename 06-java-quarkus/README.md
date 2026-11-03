# 06-java-quarkus

Mesmo domínio do Checkout (01/02) em Quarkus.

| Conceito | Spring (02) | Quarkus (06) |
|---|---|---|
| IoC / Bean | `@Component`, `@Service` | `@Singleton`, `@ApplicationScoped` (CDI/ArC) |
| Injeção | construtor / `@Autowired` | construtor / `@Inject` |
| Todas as Strategies | `List<MetodoPagamento>` | `Instance<MetodoPagamento>` |
| Config | `@Value` | `@ConfigMapping` |
| ORM | Spring Data JPA | Panache + Hibernate |
| Erros | `@RestControllerAdvice` | `@ServerExceptionMapper` |
| Resiliência | Resilience4j | SmallRye Fault Tolerance |
| Decorator de repositório | `@Primary` + `@Bean` | `@Alternative` + `@Priority` |
| Testes de infra | Testcontainers manual | Dev Services automático |

## Rodar
```bash
./up.sh                         # compose + quarkus:dev
curl -X POST localhost:8080/api/checkout \
  -H 'Content-Type: application/json' -H 'Idempotency-Key: abc-123' \
  -d '{"email":"a@b.com","valor":100,"metodo":"PIX"}'
# repita a mesma chave em < 10 s -> HTTP 409 (Redis); depois -> 409 (UNIQUE no Postgres)
```

## Testar
```bash
mvn test      # unitários (sem Docker) + integração (Dev Services, precisa de Docker)
```

## Native (GraalVM)
```bash
mvn package -Dnative            # ou: -Dnative -Dquarkus.native.container-build=true
```
