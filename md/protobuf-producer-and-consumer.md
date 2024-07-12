# Protobuf Producer and Consumer

Next, you will see how to serialize/deserialize messages using protobuf.

### Other Links

- [confluent protobuf producer and consumer examples](https://docs.confluent.io/cloud/current/sr/fundamentals/serdes-develop/serdes-protobuf.html)

### Protobuf Schema

<iframe width="560" height="315" src="https://www.youtube.com/embed/BywIOD_Y3CE"></iframe>

Protobuf allows us to serialize/deserialize messages.

```protobuf
{{#include ../kafka-protobuf/src/main/proto/Invoice.proto}}
```

### Configurations

It is possible to produce with or without Schema Registry. It'll depend on the configurations.

Producer:

```java
if (useSchemaRegistry) {
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
} else {
    // ProtobufSerializer is a custom class
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer.class);
}
```

Consumer:

```java
if (useSchemaRegistry) {
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
} else {
    // ProtobufDeserializer is a custom class
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtobufDeserializer.class);
    // here we pass a custom configuration to the deserializer
    props.put(ProtobufDeserializer.PROTOBUF_PARSER, Invoice.parser());
}
```

### Setup

Create a topic to produce protobuf messages **without** Schema Registry:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.invoices
```

Create a topic to produce protobuf messages **with** Schema Registry:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.schema.invoices
```

### Produce/Consume without Schema Registry

```bash
gradle kafka-protobuf-clients:run --args="produce client.invoices 100"
gradle kafka-protobuf-clients:run --args="consume client.invoices"
```

### Produce/Consume with Schema Registry

```bash
gradle kafka-protobuf-clients:run --args="produce -s client.schema.invoices 100"
gradle kafka-protobuf-clients:run --args="consume -s client.schema.invoices"
```