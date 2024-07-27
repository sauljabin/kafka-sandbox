# Protobuf Oneof

More at [https://protobuf.dev/reference/java/java-generated/#oneof-fields](https://protobuf.dev/reference/java/java-generated/#oneof-fields).

### Protobuf Schema

Here you can see that the `Measurement` has a `oneof` field:

```protobuf
{{#include ../kafka-protobuf/src/main/proto/Sensor.proto}}
```

### Setup

Create a topic:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.measurements
```

### Produce

```bash
gradle kafka-protobuf-oneof-clients:run --args="produce client.measurements 100"
```

### Consume

You can verify which value is inside (speed or environment) with `record.value().getValueCase()`.

```bash
gradle kafka-protobuf-oneof-clients:run --args="consume client.measurements"
```