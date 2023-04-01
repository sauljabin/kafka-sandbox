# Interactive Queries

Interactive Queries allow you to leverage the state of your application from outside your application. The Kafka Streams API enables your applications to be queryable.

- [interactive queries](https://docs.confluent.io/platform/current/streams/developer-guide/interactive-queries.html)

This example is using [gRPC](https://grpc.io/) to request queries to the kafka stream server.

Query the total supplier count by a given country:

```bash
kafka-streams count <country>
```

Example:

```bash
kafka-streams count Ecuador
```

Output:

```
Country: Ecuador, Total Suppliers: 4
```

Take into account that you have to run the stream first. Check the [Kafka Streams](./streams.md) section.

Check the stream topology:

```java
{{#include ../../../kafka-streams/src/main/java/kafka/sandbox/cli/Streams.java}}
```

Check the gRPC server:

```java
{{#include ../../../kafka-streams/src/main/java/kafka/sandbox/grpc/CounterService.java}}
```

Check the gRPC client:

```java
{{#include ../../../kafka-streams/src/main/java/kafka/sandbox/cli/Count.java}}
```