# Producing and Consuming Natives

Now we are going to develop consumers and producer with java.

### Create All the Topics

```bash
for topic in "client.string" "client.integer" "client.long" "client.float" "client.double" "client.boolean" ; do \
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic $topic ; done
```

### Produce

This code example shows you how to produce a message.
The configuration `ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG` define what type are we using.

```java
props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getSerializer());
KafkaProducer<String, V> producer = new KafkaProducer<>(props);

for (int i = 0; i < messages; i++) {
    V value = createValue();
    ProducerRecord<String, V> record = new ProducerRecord<>(
        topic,
        value
    );
    producer.send(
        record,
        (metadata, exception) -> log.info("Producing message: {}", value)
    );
}
```

Then, produce for each type:

```bash
for type in "string" "integer" "long" "float" "double" "boolean" ; do \
  gradle kafka-native-clients:run --args="produce client.$type $type 100" ; done
```

### Consume

It's going to be the same for the consumer, but in this case you have to use the **deserializer**: `ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG`.

```java
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getDeserializer());
KafkaConsumer<String, V> consumer = new KafkaConsumer<>(props);

ConsumerRecords<String, V> records = consumer.poll(Duration.ofMillis(500));

for (ConsumerRecord<String, V> record : records) {
    log.info("Supplier ID: {}", record.value());
}
```

Consume a topic, for example `client.string`:

```bash
gradle kafka-native-clients:run --args="consume client.string string"
```
