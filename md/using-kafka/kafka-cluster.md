# Kafka Cluster

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [kafka settings](https://docs.confluent.io/platform/current/installation/configuration/broker-configs.html)
- [zookeeper](https://zookeeper.apache.org/)
- [zookeeper settings](https://docs.confluent.io/platform/current/zookeeper/deployment.html)
- project location: [kafka-cluster](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-cluster)
- kafka version: [cp 7.3.2](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)
- kafka ports: `19093`, `29093`, `39093`
- kafka jmx ports: `19999`, `29999`, `39999`
- zookeeper ports: `12181`, `22181`, `32181`

Run zookeeper and kafka:

```bash
cd kafka-cluster
docker compose up -d
```

Test zookeeper and kafka (shows the broker ids):

```bash
kafka-cli zookeeper-shell zookeeper1:2181 ls /brokers/ids
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
