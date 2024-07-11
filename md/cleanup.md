# Cleanup

Shutting down all services:

```bash
docker compose --profile proxies --profile sql --profile mqtt --profile ksqldb down
```

<div class="warning">

If you want to remove the data pass `-v` at the end of the previous command.

</div>