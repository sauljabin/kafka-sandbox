# SQL Database

Create a MySQL and PostgresSQL instance and a database.

- [mysql](https://hub.docker.com/_/mysql)
- [postgres](https://hub.docker.com/_/postgres)
- [adminer](https://hub.docker.com/_/adminer)
- project location: [sql-database](https://github.com/sauljabin/kafka-sandbox/tree/main/sql-database)
- postgres port: `5432`
- mysql port: `3306`
- adminer port: `9090` ([open it in the web browser](http://localhost:9090/))

Run MySQL, PostgresSQL and Adminer:

```bash
cd sql-database
docker compose up -d
```

## Docker Compose

```yaml
{{#include ../../sql-database/docker-compose.yml}}
```