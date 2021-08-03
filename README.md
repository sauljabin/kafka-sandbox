# Kafka Sandbox

This project helps you to deploy a kafka sandbox locally.

## Dependencies

- [docker](https://www.docker.com/)
- [docker compose](https://docs.docker.com/compose/)
- [curl](https://curl.se/)
- [httpie](https://httpie.io/)
- [jq](https://stedolan.github.io/jq/)
- [java](https://www.java.com/en/download/)
- [plantuml](http://plantuml.com/)

## Get Started

Creating a network and running the cluster containers:

```bash
docker network create kafka-sandbox_network
cd kafka-cluster
docker-compose up -d
```

## Components

#### Kafka Cluster:

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [zookeeper](https://zookeeper.apache.org/)
- project location: [kafka-cluster](kafka-cluster)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`

```bash
cd kafka-cluster
docker-compose up -d
```

#### Kafka AKHQ:

UI for managing kafka cluster.

- [akhq](https://akhq.io/)
- project location: [kafka-akhq](kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

```bash
cd kafka-akhq
docker-compose up -d
```

#### Kafka Schema Registry:

It provides a RESTful interface for storing and retrieving your Avro, JSON Schema, and Protobuf schemas.

- [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html)
- [schema registry ui](https://github.com/lensesio/schema-registry-ui)
- project location: [kafka-schema-registry](kafka-schema-registry)
- schema registry port: `8081` ([open it in the web browser](http://localhost:8081/))
- schema registry ui port: `8000` ([open it in the web browser](http://localhost:8000/))

```bash
cd kafka-schema-registry
docker-compose up -d
```

#### Kafka CLI tools:

It is a collection of tools to interact with kafka cluster through the terminal.

- [kafkacat](https://github.com/edenhill/kafkacat)
- [zoe](https://adevinta.github.io/zoe/)
- [confluent community tools](https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html)
- project location: [kafka-cli-tools](kafka-cli-tools)

```bash
cd kafka-cli-tools
docker build -t kafka-cli-tools:latest .
alias kafka-cli-tools='docker run -it --network kafka-sandbox_network kafka-cli-tools:latest '
kafka-cli-tools
```

To permanently add the alias to your shell, add the following to your `~/.bashrc` or `~/.zshrc` file:

```bash
echo "alias kafka-cli-tools='docker run -it --network kafka-sandbox_network kafka-cli-tools:latest '" >> ~/.zshrc
```

#### JDBC Populate DB

This tool helps to populate either a MySQL or PostgresSQL database with random customers.
This is an ancillary project that can help us to set different scenarios.

- [mysql](https://hub.docker.com/_/mysql)
- [postgres](https://hub.docker.com/_/postgres)
- project location: [jdbc-populate-db](jdbc-populate-db)

```bash
./gradlew jdbc-populate-db:install
alias jdbc-populate-db="$PWD/jdbc-populate-db/build/install/jdbc-populate-db/bin/jdbc-populate-db "
jdbc-populate-db --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
jdbc-populate-db --url "jdbc:postgresql://localhost:5432/sandbox" --user "postgres" --password "notasecret" 100
```

To permanently add the alias to your shell, add the following to your `~/.bashrc` or `~/.zshrc` file:

```bash
echo "alias jdbc-populate-db=\"$PWD/jdbc-populate-db/build/install/jdbc-populate-db/bin/jdbc-populate-db \"" >> ~/.zshrc
```

#### Kafka Connect:

It makes it simple to quickly define connectors that move large data sets into and out of Kafka.
This example uses the [jdbc-populate-db](jdbc-populate-db) included tool.

- [connect](https://docs.confluent.io/current/connect/index.html)
- [connect api reference](https://docs.confluent.io/platform/current/connect/references/restapi.html)
- [connect ui](https://github.com/lensesio/kafka-connect-ui)
- [jdbc connector](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [adminer](https://www.adminer.org/)
- [mongo connector](https://www.confluent.io/hub/mongodb/kafka-connect-mongodb)
- [mongo express](https://github.com/mongo-express/mongo-express)
- project location: [kafka-connect](kafka-connect)
- connect port: `8082` ([open it in the web browser](http://localhost:8082/))
- connect ui port: `9000` ([open it in the web browser](http://localhost:9000/))
- adminer port: `9090` ([open it in the web browser](http://localhost:9090/))
- mongo express port: `7070` ([open it in the web browser](http://localhost:7070/))
- mongo port: `27017`
- postgres port: `5432`
- mysql port: `3306`

```bash
cd kafka-connect
docker-compose up -d
jdbc-populate-db --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
http POST http://localhost:8082/connectors < connectors/mysql-source-create-connector-payload.json
http POST http://localhost:8082/connectors < connectors/mongo-sink-create-connector-payload.json
```

#### Kafka Clients - Producer and Consumer:

Java examples for producing and consuming messages from Kafka.
These examples produce and consume messages from the `supplier` topic.
The producer example produces random suppliers.

- [kafka producer and consumer example](https://docs.confluent.io/platform/current/schema-registry/serdes-develop/serdes-avro.html)
- project location: [kafka-clients](kafka-clients)

```bash
kafka-cli-tools kafka-topics --create --bootstrap-server kafka1:19092 --replication-factor 3 --partitions 3 --topic suppliers
./gradlew kafka-clients:install
alias kafka-clients="$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients "
kafka-clients producer 100
kafka-clients consumer
```

To permanently add the alias to your shell, add the following to your `~/.bashrc` or `~/.zshrc` file:

```bash
echo "alias kafka-clients=\"$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients \"" >> ~/.zshrc
```

For creating a AVRO schema, you can use the following command (development purposes):

```bash
./gradlew kafka-clients:generateAvro
```
