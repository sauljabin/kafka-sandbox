# Avro Union

These example show you how to use [Unions](https://avro.apache.org/docs/1.10.2/spec.html#Unions).

<div class="warning">

Open a terminal inside the sandbox environment:

```bash
docker compose exec cli bash
```

</div>

### Avro Schema

In this schema we create a field `metric` that can be a `TimerMetric` or `CounterMetric`. 

```json
{{#include ../kafka-avro/src/main/avro/Metric.avsc}}
```

The `Metric` java class will define an object (for the `metric` field) instead of a specific type (`TimerMetric` or `CounterMetric`).

```java
private java.lang.Object metric;
```

### Setup

Create a topic:

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic client.metrics
```

### Produce


```bash
gradle kafka-avro-union-clients:run --args="produce client.metrics 100"
```

### Consume


```bash
gradle kafka-avro-union-clients:run --args="consume client.metrics"
```
