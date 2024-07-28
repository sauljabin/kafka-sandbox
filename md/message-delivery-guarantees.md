# Message Delivery Guarantees

**Message Delivery Guarantees** is an important concept in the kafka ecosystem.

There are three types of message sharing:

1. **At most once:** Messages are delivered once, and if there is a system failure, messages may be lost and are not
   redelivered.
2. **At least once:** This means messages are delivered one or more times. If there is a system failure, messages are
   never lost, but they may be delivered more than once.
3. **Exactly once:** This is the preferred behavior in that each message is delivered once and only once. Messages are
   never lost or read twice even if some part of the system fails.

More at [Message Delivery Guarantees](https://docs.confluent.io/kafka/design/delivery-semantics.html).

### Setup

Produce some messages:

```bash
gradle kafka-json-clients:run --args="produce client.users 100"
```

### At most once in consumers

Offsets are committed as soon as the message is received. If the processing goes wrong, the message will be lost (it
won’t be read again).

```java
private void atMostOnce() {
    while (true) {
        ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(500));

        // offsets are committed as soon as the message is received
        consumer.commitSync();

        for (ConsumerRecord<String, User> record : records) {
            try {
                businessLogic(record);
            } catch (Exception e) {
                // the exception is ignored and the message is lost
                log.warn("There was an error but it was ignored because this is at-most-once");
            }
        }
    }
}
```

Consume:

```bash
gradle kafka-delivery-guarantees-clients:run --args="consume client.users -s at-most-once"
```

### At least once in consumers

Offsets are committed after the message is processed. If the processing goes wrong, the message will be read again.
This can result in duplicate processing of messages. Make sure your processing is idempotent.

More about idempotency in system
at [Idempotency in Event-driven systems](https://www.linkedin.com/pulse/idempotency-event-driven-systems-ujjwal-gupta/).

```java
private void atLeastOnce() {
    while (true) {
        ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(500));

        for (ConsumerRecord<String, User> record : records) {
            try {
                businessLogic(record);

                // offsets after the message is processed
                consumer.commitSync(Map.of(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
            } catch (Exception e) {
                log.error("There was an error processing a message: ", e);

                // implement recovery and restart (kubernetes), dead-letter queue, etc

                // throw the exception up
                throw e;
            }
        }

    }
}
```

Consume:

```bash
gradle kafka-delivery-guarantees-clients:run --args="consume client.users -s at-least-once"
```

### Exactly once in consumers

It's important to say that kafka support **exactly once semantic** through **transaction**.
Keep in mind that **transactions** in Kafka were developed specifically
for **stream processing applications**. And therefore they were built to work with the
**consume-process-produce** pattern that forms the basis of stream processing applications.

Kafka does not support exactly once when:

- Publish/subscribe pattern (common producer and consumer)
- Reading from a Kafka topic and writing to a database
- Reading data from a database, writing to Kafka, and from there writing to another database
- Copying data from one Kafka cluster to another
- Any other side effects while stream processing (calling a REST API, writing to a file, sending an email, etc)

> [!TIP]
> If you want to build a **stream processing application** (sum, max, min, count, map, filter, etc) it is highly
> recommended to use
> [kafka stream](kafka-streams.md).

> [!WARNING]
> Is highly recommended to read the chapter 8
> of [Kafka: The Definitive Guide, 2nd Edition](https://www.oreilly.com/library/view/kafka-the-definitive/9781492043072/).

> [!NOTE]
> _Microservices often need to update the database and publish a
> message to Kafka within a single atomic transaction, so either both
> will happen or neither will... Kafka transactions will not do this.
> A common solution to this common problem is known as the **outbox pattern**.
> The microservice only publishes the message to a
> Kafka topic (**the outbox**), and a separate message relay service
> reads the event from Kafka and updates the database. Because... Kafka won’t guarantee an exactly-once update to
> the database, it is important to make sure the update is idempotent._
>
> **Kafka: The Definitive Guide, 2nd Edition**

More about the **outbox pattern** at: [Reliable Microservices Data Exchange With the Outbox Pattern](https://debezium.io/blog/2019/02/19/reliable-microservices-data-exchange-with-the-outbox-pattern/).