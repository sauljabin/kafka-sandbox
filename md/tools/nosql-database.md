# NoSQL Database

Create a MongoDB instance and a database.

- [mongo](https://hub.docker.com/_/mongo)
- [mongo express](https://hub.docker.com/_/mongo-express)
- project location: [nosql-database](https://github.com/sauljabin/kafka-sandbox/tree/main/nosql-database)
- mongo port: `27017`
- mongo express port: `7070` ([open it in the web browser](http://localhost:7070/))

Run MongoDB and Mongo Express:

```bash
cd nosql-database
docker compose up -d
```

## Docker Compose

```yaml
{{#include ../../nosql-database/docker-compose.yml}}
```