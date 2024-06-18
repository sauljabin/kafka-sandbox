# ksqlDB Queries

### Create a Stream

Create orders stream:

```bash
docker compose exec ksqldb-cli ksql -f /sandbox/ksql/create-orders.ksql http://ksqldb:8088
```

Previous command executed this:

```sql
{{#include ../kafka-ksqldb/ksql/create-orders.ksql}}
```

List of streams:

```bash
docker compose exec ksqldb-cli ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

### Insert

As any other SQL interpreter, ksqlDB will use the command `INSERT` to populate a table.

```sql
{{#include ../kafka-ksqldb/ksql/insert-orders.ksql}}
```

### Show

Insert orders:

```bash
docker compose exec ksqldb-cli ksql -f /sandbox/ksql/insert-orders.ksql http://ksqldb:8088
```

Show content:

```bash
docker compose exec ksqldb-cli ksql -e "PRINT 'ksqldb.order_sizes' FROM BEGINNING;" http://ksqldb:8088
```
