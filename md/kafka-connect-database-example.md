# Kafka Connect Database Example

In this example you are going to learn how to move data from a source (**mysql**),
to multiple targets (**postgres and mongo**).

> This example does not support deletion, for that you have to implement tombstone events at the [source](https://debezium.io/documentation/reference/connectors/postgresql.html#postgresql-tombstone-events) and [sink](https://docs.confluent.io/kafka-connect-jdbc/current/sink-connector/index.html#jdbc-sink-delete-mode).

### Populate Database

Run MySQL and PostgreSQL:

```bash
docker compose --profile sql up -d
```

Populate it:

```bash
mysql --host=127.0.0.1 --port=3306 \
      --user=root --password=notasecret \
      --database=sandbox \
      < kafka-connect/sql/customers.sql
```

That command should have created the table `customers` and inserted 200 records.

Now you can open [Adminer](http://localhost:9090) or run:

```bash
mysql --host=127.0.0.1 --port=3306 \
      --user=root --password=notasecret \
      --database=sandbox \
      -e "select * from customers"
```

### Create Source Connector

Check the installed plugins:

```bash
http :8083/connector-plugins
```

Now you have to hit the kafka connect rest service to create a new source, next you have the rest payload:

```json
{{#include ../kafka-connect/requests/create-connector-mysql-source.json}}
```

Create the connector using the API:

```bash
http :8083/connectors < kafka-connect/requests/create-connector-mysql-source.json
```

If you open [AKHQ](http://localhost:8080) you should see a new topic: `connect.customers`.

### Create Sink Connector

Payload:

```json
{{#include ../kafka-connect/requests/create-connector-postgres-sink.json}}
```

Create sink connector:

```bash
http :8083/connectors < kafka-connect/requests/create-connector-postgres-sink.json
```

This sink connector is going to create a table `customers` on postgres and insert all records.

Now you can open [Adminer](http://localhost:9090) or run:

```bash
PGPASSWORD=notasecret psql --host=127.0.0.1 --port=5432 \
      --user=postgres --dbname=sandbox\
      -c "select * from customers"
```

List connector:

```bash
http :8083/connectors
```

### Deleting Connectors

```bash
http DELETE :8083/connectors/postgres-sink
http DELETE :8083/connectors/mysql-source
```
