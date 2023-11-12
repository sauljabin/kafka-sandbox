# Kafka Cluster

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [kafka settings](https://docs.confluent.io/platform/current/installation/configuration/broker-configs.html)
- project location: [kafka-cluster](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-cluster)
- kafka version: [cp 7.5.2](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)
- kafka ports: `19092`, `29092`, `39092`
- kafka jmx ports: `19999`, `29999`, `39999`

Run kafka cluster:

```bash
cd kafka-cluster
docker compose up -d
cd ..
```

Create a topic:

```bash
kafka-cli kafka-topics --create --bootstrap-server kafka1:9092 \
                       --replication-factor 3 \
                       --partitions 3 \
                       --topic kafka-cluster.test
kafka-cli kafka-topics --bootstrap-server kafka1:9092 --list
```

Produce a message:

```bash
kafka-cli kafka-console-producer --broker-list kafka1:9092 --topic kafka-cluster.test
```

Consume messages:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-cluster.test \
                                 --topic kafka-cluster.test  \
                                 --bootstrap-server kafka1:9092
```

> ⚠️ The `JMX` ports were opened to monitor kafka using `jconsole`.

Run `jconsole`:

```bash
jconsole localhost:19999
```

## Docker Compose

```yaml
{{#include ../../kafka-cluster/docker-compose.yml}}
```
