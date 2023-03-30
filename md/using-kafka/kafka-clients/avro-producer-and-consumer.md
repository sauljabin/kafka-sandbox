# Avro Producer and Consumer

These examples produce and consume messages from the `supplier` topic. The producer example produces random suppliers.

- [kafka producer and consumer example](https://docs.confluent.io/platform/current/schema-registry/serdes-develop/serdes-avro.html)
- [kafka consumer settings](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html)
- [kafka producer settings](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html)
- avro project location: [kafka-avro](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-avro)
- project location: [kafka-clients](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-clients)

> ⚠️ Run these commands inside the root folder.

Create an alias for `kafka-clients`:

```bash
alias kafka-clients="$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-clients='$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients '" >> ~/.zshrc
```

Create a topic:

```bash
kafka-cli kafka-topics --create --bootstrap-server kafka1:9092 \
                       --replication-factor 3 \
                       --partitions 3 \
                       --topic kafka-clients.suppliers
```

Install the app:

```bash
./gradlew kafka-clients:install
kafka-clients
```

Run clients:

```bash
kafka-clients producer 100
kafka-clients consumer
```

For creating a AVRO schema, you can use the following command (development purposes):

```bash
./gradlew kafka-avro:build
```

## Avro Schema

#### suppliers-v1.avsc

```json
{{#include ../../../kafka-avro/src/main/avro/suppliers-v1.avsc}}
```