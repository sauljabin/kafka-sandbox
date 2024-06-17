# What is Kafka?

<iframe width="560" height="315" src="https://www.youtube.com/embed/06iRM1Ghr1k"></iframe>

Let's deploy a three node kafka cluster on the ports: `19092`, `29092`, `39092`:

```bash
docker compose up -d
```

Create a topic:

<iframe width="560" height="315" src="https://www.youtube.com/embed/kj9JH3ZdsBQ"></iframe>

```bash
kafka-topics --create \
             --bootstrap-server localhost:19092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic sandbox.test
```

List topics:

```bash
kafka-topics --list \
             --bootstrap-server localhost:19092
```

At this point you must understand that the most important concept for you is the partition.
You will be tempted to focus on the topic concept, but you should focus on the partition.
This is due to the very nature of a distributed system.

<iframe width="560" height="315" src="https://www.youtube.com/embed/y9BStKvVzSs"></iframe>
