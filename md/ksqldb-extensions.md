# ksqlDB Extensions

ksqlDB extensions are pieces of logic for transforming or aggregating events that ksqlDB can't currently express.

- [ksqldb extensions (udf, udtf, udaf)](https://docs.ksqldb.io/en/latest/how-to-guides/create-a-user-defined-function)

Check the [Kafka ksqlDB](what-is-ksqldb.md) section.

For creating the `jar` extension, you can use the following command (development purposes):

```bash
gradle kafka-ksqldb-extensions:shadowJar
```

## Custom UDF

```java
{{#include ../kafka-ksqldb-extensions/src/main/java/kafka/sandbox/ksqldb/TaxesUdf.java}}
```