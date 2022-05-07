# Streams

Kafka Streams is a client library providing organizations with a particularly efficient framework for processing
streaming data. It offers a streamlined method for creating applications and microservices that must process data in
real-time to be effective.

Check the [Kafka Clients - Avro Producer and Consumer](#kafka-clients---avro-producer-and-consumer) section.

- [kafka streams](https://kafka.apache.org/documentation/streams/)
- [kafka streams examples](https://github.com/confluentinc/kafka-streams-examples)
- project location: [kafka-clients](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-clients)

Run streams:

```bash
kafka-clients streams
```

Print results:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-streams.consumer \
                                 --topic kafka-streams.supplier_counts_by_country  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true \
                                 --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```