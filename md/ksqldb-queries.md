# ksqlDB Queries

### Create a Stream

Create orders stream:

```bash
ksql -f kafka-ksqldb/ksql/create-orders.ksql http://ksqldb:8088
```

The previous command executed this:
```sql
{{#include ../kafka-ksqldb/ksql/create-orders.ksql}}
```

List of streams:

```bash
ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

### Insert

As any other SQL interpreter, ksqlDB will use the command `INSERT` to populate a table.

```sql
{{#include ../kafka-ksqldb/ksql/insert-orders.ksql}}
```

Insert orders:

```bash
ksql -f kafka-ksqldb/ksql/insert-orders.ksql http://ksqldb:8088
```

### Show

```bash
ksql -e "PRINT 'ksqldb.order_sizes' FROM BEGINNING;" http://ksqldb:8088
```
