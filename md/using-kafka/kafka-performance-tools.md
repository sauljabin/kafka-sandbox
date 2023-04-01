# Kafka Performance Tools

Performance tuning involves two important metrics:

- Latency measures how long it takes to process one event.
- Throughput measures how many events arrive within a specific amount of time.

Run help:

```bash
kafka-cli kafka-producer-perf-test --help
kafka-cli kafka-consumer-perf-test --help
```

Create a topic:

```bash
kafka-cli kafka-topics --create --bootstrap-server kafka1:9092 \
                       --replication-factor 3 \
                       --partitions 3 \
                       --topic kafka-cluster.performance-test
```

## Performance Tests

Test producer:

```bash
kafka-cli kafka-producer-perf-test --topic kafka-cluster.performance-test \
                                   --throughput -1 \
                                   --num-records 3000000 \
                                   --record-size 1024 \
                                   --producer-props acks=all bootstrap.servers=kafka1:9092,kafka2:9092,kafka3:9092
```

- Throughput in MB/sec.
- Latency in milliseconds.

Test consumer:

```bash
kafka-cli kafka-consumer-perf-test --topic kafka-cluster.performance-test \
                                   --broker-list kafka1:9092,kafka2:9092,kafka3:9092 \
                                   --messages 3000000
```

- `start.time, end.time`: shows test start and end time.
- `data.consumed.in.MB`: shows the size of all messages consumed.
- `MB.sec`: shows how much data transferred in megabytes per second (Throughput on size).
- `data.consumed.in.nMsg`: shows the count of the total messages consumed during this test.
- `nMsg.sec`: shows how many messages were consumed in a second (Throughput on the count of messages).

Test end to end latency:

```bash
kafka-cli kafka-run-class kafka.tools.EndToEndLatency kafka1:9092,kafka2:9092,kafka3:9092 kafka-cluster.performance-test 10000 1 1024
```

- This class records the average end to end latency for a single message to travel through Kafka.
- Latency in milliseconds.