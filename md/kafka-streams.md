# Kafka Streams

Kafka Streams is a client library providing organizations with a particularly efficient framework for processing
streaming data. It offers a streamlined method for creating applications and microservices that must process data in
real-time to be effective.

- [kafka streams examples](https://github.com/confluentinc/kafka-streams-examples)
- more kafka streams examples [here](https://github.com/sauljabin/kafka-streams-sandbox).
- [kafka stream architecture](https://docs.confluent.io/platform/current/streams/architecture.html)

<iframe width="560" height="315" src="https://www.youtube.com/embed/y9a3fldlvnI"></iframe>

In this example we are going to create a data processing pipeline (topology), which
is going to count the suppliers by country (check the [Kafka Clients - Avro Producer and Consumer](avro-producer-and-consumer.md) section).
So, kafka stream is going to consume form the `client.suppliers` topic, then is
going to process the data and finally is going to publish the results in the 
topic `streams.results`.

```java
// read from suppliers topic
KStream<String, Supplier> suppliers = builder.stream(source);

// aggregate the new supplier counts by country
KTable<String, Long> aggregated = suppliers
        // map the country as key
        .map((key, value) -> new KeyValue<>(value.getCountry().toString(), value))
        .groupByKey()
        // aggregate and materialize the store
        .count(Materialized.as("SupplierCountByCountry"));

// write the results to a topic
aggregated.toStream()
        // print results
        .peek((key, value) -> log.info("Country = {}, Total supplier counts = {}", key, value))
        // publish results
        .to(sink, Produced.with(Serdes.String(), Serdes.Long()));

// build the topology
Topology topology = builder.build();
```

### Run Topology

<div class="warning">

Open a terminal inside the sandbox environment:

```bash
docker compose exec cli bash
```

</div>

```bash
gradle kafka-streams:run --args="streams client.suppliers streams.results"
```

Print results (in another terminal):

```bash
kafka-console-consumer --from-beginning --group kafka-streams.consumer \
                       --topic streams.results  \
                       -bootstrap-server kafka1:9092 \
                       --property print.key=true \
                       --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

Send new suppliers (in another terminal):

```bash
gradle kafka-avro-clients:run --args="produce client.suppliers 100"
```
