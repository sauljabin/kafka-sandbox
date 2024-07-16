# What is ksqlDB?

ksqlDB is a database that's purpose-built for stream processing applications.
ksqlDB it is not a SQL database, it provides an extra layer for implementing kstream, ktable and connectors through a language (ksql) based on sql.

<iframe width="560" height="315" src="https://www.youtube.com/embed/Ji7YMlJUqsA"></iframe>

- [ksqldb](https://ksqldb.io/)
- [ksqldb settings](https://docs.ksqldb.io/en/latest/reference/server-configuration/)

### Run ksqlDB

Check if it's up:

```bash
http ksqldb:8088/info
```

One line shell interaction:

```bash
ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

Interactive ksqlDB shell:

```bash
ksql http://ksqldb:8088
```

Then enter `SHOW STREAMS;`.