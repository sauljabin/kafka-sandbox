# Cleanup

Shutting down all services:

```bash
docker compose --profile proxies --profile sql --profile mqtt --profile ksqldb down
```

Remove with data:

```bash
docker compose --profile proxies --profile sql --profile mqtt --profile ksqldb down -v
```