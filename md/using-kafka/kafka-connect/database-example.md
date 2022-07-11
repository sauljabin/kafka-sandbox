# Kafka Connect Database Example

> ⚠️ This example does not support deletion, for that you have to implement tombstone events at the [source](https://debezium.io/documentation/reference/connectors/postgresql.html#postgresql-tombstone-events) and [sink](https://docs.confluent.io/kafka-connect-jdbc/current/sink-connector/index.html#jdbc-sink-delete-mode).

Populate the databases:

```bash
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
```

Create the connectors using the API:

```bash
cd kafka-connect
http :8083/connectors < requests/create-connector-mysql-source.json
http :8083/connectors < requests/create-connector-mongo-sink.json
http :8083/connectors < requests/create-connector-postgres-sink.json
```

For deleting the connectors:

```bash
http DELETE :8083/connectors/postgres-sink
http DELETE :8083/connectors/mongo-sink
http DELETE :8083/connectors/mysql-source
```

## Requests

#### requests/create-connector-mysql-source.json

```json
{{#include ../../../kafka-connect/requests/create-connector-mysql-source.json}}
```

#### requests/create-connector-mongo-sink.json

```json
{{#include ../../../kafka-connect/requests/create-connector-mongo-sink.json}}
```

#### requests/create-connector-postgres-sink.json

```json
{{#include ../../../kafka-connect/requests/create-connector-postgres-sink.json}}
```