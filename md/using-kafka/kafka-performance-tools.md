# Kafka Performance Tools

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

Test producer:

```bash
kafka-cli kafka-producer-perf-test --topic kafka-cluster.performance-test \
                                   --throughput -1 \
                                   --num-records 3000000 \
                                   --record-size 1024 \
                                   --producer-props acks=all bootstrap.servers=kafka1:9092,kafka2:9092,kafka3:9092
```

Test consumer:

```bash
kafka-cli kafka-consumer-perf-test --topic kafka-cluster.performance-test \
                                   --broker-list kafka1:9092,kafka2:9092,kafka3:9092 \
                                   --messages 3000000
```

Test latency:

```bash
kafka-cli kafka-run-class kafka.tools.EndToEndLatency kafka1:9092,kafka2:9092,kafka3:9092 kafka-cluster.performance-test 10000 1 1024
```