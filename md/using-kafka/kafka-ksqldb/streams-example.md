# ksqlDB Streams Example

- statements location: [kafka-ksqldb/statements](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-ksqldb/statements)
- test location: [kafka-ksqldb/tests](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-ksqldb/tests)

Test runner:

```bash
ksqldb-cli ksql-test-runner -e extensions/ \
                            -s statements/create-orders.ksql \
                            -i tests/orders-input.json \
                            -o tests/orders-output.json | grep '>>>'
```

Execute statement files:

```bash
ksqldb-cli ksql -f statements/create-orders.ksql http://ksqldb:8088
ksqldb-cli ksql -f statements/insert-orders.ksql http://ksqldb:8088
```

List of streams:

```bash
http :8088/ksql ksql="list streams;" | jq '.[].streams[] | [{name: .name, topic: .topic}]'
```

Show content:

```bash
ksqldb-cli ksql -e "PRINT 'kafka-ksqldb.order_sizes' FROM BEGINNING;" http://ksqldb:8088
```

Deleting all orders:

```bash
ksqldb-cli ksql -e "DROP STREAM ORDERSIZES DELETE TOPIC; DROP STREAM ORDERS DELETE TOPIC;" http://ksqldb:8088
```

## Statements

#### statements/create-orders.ksql

```sql
{{#include ../../../kafka-ksqldb/statements/create-orders.ksql}}
```

#### statements/insert-orders.ksql

```sql
{{#include ../../../kafka-ksqldb/statements/insert-orders.ksql}}
```

## Tests

#### tests/orders-input.json

```json
{{#include ../../../kafka-ksqldb/tests/orders-input.json}}
```

#### tests/orders-output.json

```json
{{#include ../../../kafka-ksqldb/tests/orders-output.json}}
```