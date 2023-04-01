# Streams

Kafka Streams is a client library providing organizations with a particularly efficient framework for processing
streaming data. It offers a streamlined method for creating applications and microservices that must process data in
real-time to be effective.

Check the [Kafka Clients - Avro Producer and Consumer](../kafka-clients/avro-producer-and-consumer.md) section.

- [kafka streams](https://kafka.apache.org/documentation/streams/)
- [kafka streams examples](https://github.com/confluentinc/kafka-streams-examples)
- more kafka streams examples [here](https://github.com/sauljabin/kafka-streams-sandbox).
- project location: [kafka-streams](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-streams)

> ⚠️ Run these commands inside the root folder.

Create an alias for `kafka-streams`:

```bash
alias kafka-streams="$PWD/kafka-streams/build/install/kafka-streams/bin/kafka-streams "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-streams='$PWD/kafka-streams/build/install/kafka-streams/bin/kafka-streams '" >> ~/.zshrc
```

Install the app:

```bash
./gradlew kafka-streams:install
kafka-streams
```

Run streams:

```bash
kafka-streams streams
```

Print results:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-streams.consumer \
                                 --topic kafka-streams.supplier_counts_by_country  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true \
                                 --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```
