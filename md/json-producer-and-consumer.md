# JSON Producer and Consumer

This example shows you how to use json and schema registry for producing and consuming.

### Other Links

- [json schema](https://json-schema.org/)
- [confluent example](https://docs.confluent.io/platform/current/schema-registry/fundamentals/serdes-develop/serdes-json.html)

### POJO

The first step is to create the structure:

```java
{{#include ../kafka-json-clients/src/main/java/kafka/sandbox/cli/User.java}}
```

Confluent java library uses [jackson annotations](https://github.com/FasterXML/jackson-annotations?tab=readme-ov-file#usage-simple).

### Configurations

It is possible to produce with or without Schema Registry. It'll depend on the configurations.

Producer:

```java
if (useSchemaRegistry) {
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
} else {
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);
}
```

Consumer:

```java
if (useSchemaRegistry) {
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
} else {
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
}
```

### Setup

Create a topic to produce json **without** Schema Registry:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.users
```

Create a topic to produce json **with** Schema Registry:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.schema.users
```

### Produce

Produce **without** Schema Registry:

```bash
gradle kafka-json-clients:run --args="produce client.users 100"
```

Produce **with** Schema Registry:

```bash
gradle kafka-json-clients:run --args="produce -s client.schema.users 100"
```

### Consume

Consume **without** Schema Registry:

```bash
gradle kafka-json-clients:run --args="consume client.users"
```

Consume **with** Schema Registry:

```bash
gradle kafka-json-clients:run --args="consume -s client.schema.users"
```