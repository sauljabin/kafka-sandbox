# ksqlDB Extensions

ksqlDB extensions are pieces of logic for transforming or aggregating events that ksqlDB can't currently express.

- [ksqldb extensions (udf, udtf, udaf)](https://docs.ksqldb.io/en/latest/how-to-guides/create-a-user-defined-function)
- project location: [kafka-ksqldb-extensions](kafka-ksqldb-extensions)

Check the [Kafka ksqlDB](#kafka-ksqldb) section.

For creating the `jar` extension, you can use the following command (development purposes):

```bash
./gradlew kafka-ksqldb-extensions:shadowJar
```