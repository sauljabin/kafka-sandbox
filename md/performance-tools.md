# Performance Tools

Performance tuning involves two important metrics:

- Latency measures how long it takes to process one event.
- Throughput measures how many events arrive within a specific amount of time.

Run help:

```bash
kafka-producer-perf-test --help
kafka-consumer-perf-test --help
```

Create a topic:

```bash
kafka-topics --create \
             --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic sandbox.performance
```

### Performance Tests

Test
producer ([confluent doc](https://docs.confluent.io/kafka/operations-tools/kafka-tools.html#kafka-producer-perf-test-sh)):

```bash
kafka-producer-perf-test --topic sandbox.performance \
                         --throughput -1 \
                         --num-records 3000000 \
                         --record-size 1024 \
                         --producer-props acks=all bootstrap.servers=localhost:19092
```

- Throughput in MB/sec.
- Latency in milliseconds.

Test
consumer ([confluent doc](https://docs.confluent.io/kafka/operations-tools/kafka-tools.html#kafka-consumer-perf-test-sh)):

```bash
kafka-consumer-perf-test --topic sandbox.performance \
                         --bootstrap-server localhost:19092 \
                         --messages 3000000
```

- `start.time, end.time`: shows test start and end time.
- `data.consumed.in.MB`: shows the size of all messages consumed.
- `MB.sec`: shows how much data transferred in megabytes per second (Throughput on size).
- `data.consumed.in.nMsg`: shows the count of the total messages consumed during this test.
- `nMsg.sec`: shows how many messages were consumed in a second (Throughput on the count of messages).

Test end to end
latency ([confluent doc](https://docs.confluent.io/kafka/operations-tools/kafka-tools.html#kafka-e2e-latency-sh)):

```bash
kafka-e2e-latency localhost:19092 sandbox.performance 10000 all 1024
```

Following are the required arguments:

- `broker_list`: The location of the bootstrap broker for both the producer and the consumer.
- `topic`: The topic name used by both the producer and the consumer to send/receive messages.
- `num_messages`: The number of messages to send
- `producer_acks`: The producer setting for acks.
- `message_size_bytes`: size of each message in bytes.