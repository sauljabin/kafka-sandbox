# Interactive Queries

This example is using gRPC to request queries to the kafka stream server. 

Query the total count by a given country:

```bash
kafka-streams count <country>
```

Example:

```bash
kafka-streams count Ecuador
```

Output:

```
Ecuador, Total Suppliers: 4
```

Take into account that you have to run the stream firts. Check the [Kafka Streams](./streams.md) section.

Check the stream topology and the gRPC server:

```yaml
{{#include ../../../kafka-streams/src/main/java/kafka/sandbox/cli/Streams.java}}
```

Check the gRPC clinet:

```yaml
{{#include ../../../kafka-streams/src/main/java/kafka/sandbox/cli/Count.java}}
```