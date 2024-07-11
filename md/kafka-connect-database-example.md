# Kafka Connect Database Example

In this example you are going to learn how to move data from a source (**mysql**),
to multiple targets (**postgres and mongo**).

<div class="warning">

This example does not support deletion, for that you have to implement tombstone events at the [source](https://debezium.io/documentation/reference/connectors/postgresql.html#postgresql-tombstone-events) and [sink](https://docs.confluent.io/kafka-connect-jdbc/current/sink-connector/index.html#jdbc-sink-delete-mode).

</div>


### Populate Database

Run MySQL and PostgreSQL:

```bash
docker compose --profile sql up -d
```

Then open a terminal inside the sandbox environment:

```bash
docker compose exec cli bash
```

Populate it:

```bash
mysql --host=mysql --port=3306 \
      --user=root --password=notasecret \
      --database=sandbox \
      < kafka-connect/sql/customers.sql
```

That command should have created the table `customers` and inserted 200 records.

Now you can open [Adminer](http://localhost:9090) or run:

```bash
mysql --host=mysql --port=3306 \
      --user=root --password=notasecret \
      --database=sandbox \
      -e "select * from customers"
```

### Create Source Connector

Check the installed plugins:

```bash
http kafka-connect:8083/connector-plugins
```

Now you have to hit the kafka connect rest service to create a new source, next you have the rest payload:

```json
{{#include ../kafka-connect/requests/create-connector-mysql-source.json}}
```

Create the connector using the API:

```bash
http kafka-connect:8083/connectors < kafka-connect/requests/create-connector-mysql-source.json
```

If you open [AKHQ](http://localhost:8080) you should see a new topic: `connect.customers`.

### Create Sink Connector

Payload:

```json
{{#include ../kafka-connect/requests/create-connector-postgres-sink.json}}
```

Create sink connector:

```bash
http kafka-connect:8083/connectors < kafka-connect/requests/create-connector-postgres-sink.json
```

This sink connector is going to create a table `customers` on postgres and insert all records.

Now you can open [Adminer](http://localhost:9090) or run:

```bash
psql --host=postgres --port=5432 \
     --user=postgres --dbname=sandbox\
     -c "select * from customers"
```

List connector:

```bash
http kafka-connect:8083/connectors
```

### Deleting Connectors

```bash
http DELETE kafka-connect:8083/connectors/postgres-sink
http DELETE kafka-connect:8083/connectors/mysql-source
```