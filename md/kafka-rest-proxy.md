# Kafka REST Proxy

The Kafka REST Proxy provides a RESTful interface to a Kafka cluster.
Use this when you really need a rest interface since it is usually more complex than using conventional kafka clients.
You can check the API reference [here](https://docs.confluent.io/platform/current/kafka-rest/api.html).

### Setup

Run Kafka REST Proxy:

```bash
docker compose --profile proxies up -d
```

Check the cluster information:

```bash
http kafka-rest:8082/v3/clusters
```

### Create Topic

Payload:

```json
{{#include ../kafka-rest/requests/create-topic.json}}
```

Hit rest proxy:

```bash
http kafka-rest:8082/v3/clusters/${CLUSTER_ID}/topics < kafka-rest/requests/create-topic.json
```

List topics:

```bash
http kafka-rest:8082/v3/clusters/${CLUSTER_ID}/topics | jq -r '.data[].topic_name'
```

### Produce

Payload:

```json
{{#include ../kafka-rest/requests/produce-avro-message.json}}
```

Send payload:

```bash
http kafka-rest:8082/v3/clusters/${CLUSTER_ID}/topics/proxy.rest/records < kafka-rest/requests/produce-avro-message.json
```

### Consume

```bash
kafka-avro-console-consumer --bootstrap-server kafka1:9092 \
        --topic proxy.rest \
        --from-beginning \
        --property schema.registry.url=http://schema-registry:8081
```