# Kafka AKHQ

UI for managing kafka cluster.

- [akhq](https://akhq.io/)
- [akhq settings](https://github.com/tchiotludo/akhq#kafka-cluster-configuration)
- project location: [kafka-akhq](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

Run AKHQ:

```bash
cd kafka-akhq
docker compose up -d
```

## Docker Compose

```yaml
{{#include ../../kafka-akhq/docker-compose.yml}}
```