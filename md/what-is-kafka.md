# What is Kafka?

<iframe width="560" height="315" src="https://www.youtube.com/embed/06iRM1Ghr1k"></iframe>

### Create a Topic

<iframe width="560" height="315" src="https://www.youtube.com/embed/kj9JH3ZdsBQ"></iframe>

<div class="warning">

Open a terminal inside the sandbox environment:

```bash
docker compose exec cli bash
```

</div>

```bash
kafka-topics --create --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic sandbox.test
```

### List Topics

```bash
kafka-topics --list --bootstrap-server kafka1:9092
```

At this point you must understand that the most important concept for you is the partition.
You will be tempted to focus on the topic concept, but you should focus on the partition.
This is due to the very nature of a distributed system.

<iframe width="560" height="315" src="https://www.youtube.com/embed/y9BStKvVzSs"></iframe>
