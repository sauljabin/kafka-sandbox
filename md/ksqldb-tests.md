# ksqlDB Tests

- [ksqldb test runner](https://docs.ksqldb.io/en/latest/how-to-guides/test-an-app/)

One interesting feature that ksqlDB has is the test runner, it allows you to test a query before deploying it.

### Run a Test

```bash
docker compose exec ksqldb-cli ksql-test-runner -e /extensions/ \
                            -s /sandbox/ksql/create-orders.ksql \
                            -i /sandbox/tests/orders-input.json \
                            -o /sandbox/tests/orders-output.json | grep '>>>'
```


## Tests

A test has 2 parts, inputs and outputs, the ksqldb test runner will compare then to define if
the test passes or fails.

#### orders-input.json

```json
{{#include ../kafka-ksqldb/tests/orders-input.json}}
```

#### orders-output.json

```json
{{#include ../kafka-ksqldb/tests/orders-output.json}}
```