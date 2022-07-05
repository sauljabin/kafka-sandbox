# Kafka Schema Registry

It provides a RESTful interface for storing and retrieving your Avro, JSON Schema, and Protobuf schemas.

- [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html)
- [schema registry settings](https://docs.confluent.io/platform/current/schema-registry/installation/config.html)
- project location: [kafka-schema-registry](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-schema-registry)
- schema registry port: `8081`

Run Schema Registry:

```bash
cd kafka-schema-registry
docker compose up -d
http :8081/config
```

## Docker Compose

```yaml
{{#include ../../kafka-schema-registry/docker-compose.yml}}
```