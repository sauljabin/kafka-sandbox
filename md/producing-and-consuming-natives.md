# Producing and Consuming Natives

Now we are going to develop consumers and producer with java.

## Setup

Create a topic:

```bash
kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.string
             
kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.integer

kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.long

kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.float

kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.double

kafka-topics --create --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.boolean
```

### Produce

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

```bash
./gradlew kafka-native-clients:run --args="produce client.string string 100"
./gradlew kafka-native-clients:run --args="produce client.integer integer 100"
./gradlew kafka-native-clients:run --args="produce client.long long 100"
./gradlew kafka-native-clients:run --args="produce client.float float 100"
./gradlew kafka-native-clients:run --args="produce client.double double 100"
./gradlew kafka-native-clients:run --args="produce client.boolean boolean 100"
```

### Consume

```java
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getDeserializer());
KafkaConsumer<String, V> consumer = new KafkaConsumer<>(props);

ConsumerRecords<String, V> records = consumer.poll(Duration.ofMillis(500));

for (ConsumerRecord<String, V> record : records) {
    log.info("Supplier ID: {}", record.value());
}
```

```bash
./gradlew kafka-native-clients:run --args="consume client.<TYPE> <TYPE>"
```
