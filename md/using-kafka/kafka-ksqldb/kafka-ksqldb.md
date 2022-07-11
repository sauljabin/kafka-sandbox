# Kafka ksqlDB

ksqlDB is a database that's purpose-built for stream processing applications.

> ⚠️ ksqlDB it is not a SQL database, it provides an extra layer for implementing kstream, ktable and connectors through a language (ksql) based on sql.

- [ksqldb](https://ksqldb.io/)
- [ksqldb settings](https://docs.ksqldb.io/en/latest/reference/server-configuration/)
- [ksqldb test runner](https://docs.ksqldb.io/en/latest/how-to-guides/test-an-app/)
- project location: [kafka-ksqldb](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-ksqldb)
- ksqldb port: `8088`

Create an alias for `ksqldb-cli`:

> ⚠️ Run alias commands inside the root folder.

```bash
alias ksqldb-cli="docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias ksqldb-cli='docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest '" >> ~/.zshrc
```

Run ksqlDB:

```bash
cd kafka-ksqldb
docker compose up -d
http :8088/info
```

One line shell interaction:

```bash
ksqldb-cli ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

Interactive ksqlDB shell:

```bash
ksqldb-cli ksql http://ksqldb:8088
SHOW STREAMS;
```

## Docker Compose

```yaml
{{#include ../../../kafka-ksqldb/docker-compose.yml}}
```
