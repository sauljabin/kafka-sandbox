# Kafka Sandbox

This project helps you to deploy a kafka sandbox locally.

## Dependencies

- [docker](https://www.docker.com/)
- [docker compose](https://docs.docker.com/compose/)
- [curl](https://curl.se/)
- [httpie](https://httpie.io/)
- [jq](https://stedolan.github.io/jq/)
- [java](https://www.java.com/en/download/)

## Interesting Links

- [confluent docker images references](https://docs.confluent.io/platform/current/installation/docker/image-reference.html)
- [confluent versions interoperability](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)
- [confluent free courses](https://developer.confluent.io/learn-kafka/)

## Get Started

Creating a network and running the cluster containers:

```bash
docker network create kafka-sandbox_network
cd kafka-cluster
docker-compose up -d
```

## Tools

#### Kafka CLI Tools:

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

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-cli-tools='docker run -it --network kafka-sandbox_network kafka-cli-tools:latest '" >> ~/.zshrc
```

#### SQL Server:

Create a SQL (MySQL or PostgresSQL) server instance and a database.

- [mysql](https://hub.docker.com/_/mysql)
- [postgres](https://hub.docker.com/_/postgres)
- [adminer](https://www.adminer.org/)
- project location: [sql-server](sql-server)
- postgres port: `5432`
- mysql port: `3306`
- adminer port: `9090` ([open it in the web browser](http://localhost:9090/))

```bash
cd sql-server
docker-compose up -d
```

#### SQL Populate DB:

This tool helps to populate either a MySQL or PostgresSQL database with random customers.
This is an ancillary project that can help us to set different scenarios.

- project location: [sql-populate](sql-populate)

```bash
./gradlew sql-populate:install
alias sql-populate="$PWD/sql-populate/build/install/sql-populate/bin/sql-populate "
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
sql-populate --url "jdbc:postgresql://localhost:5432/sandbox" --user "postgres" --password "notasecret" 100
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias sql-populate='$PWD/sql-populate/build/install/sql-populate/bin/sql-populate '" >> ~/.zshrc
```

#### NoSQL Server:

Create a NoSQL (MongoDB) server instance and a database.

- [mongo](https://hub.docker.com/_/mongo)
- [mongo express](https://github.com/mongo-express/mongo-express)
- project location: [nosql-server](nosql-server)
- mongo port: `27017`
- mongo express port: `7070` ([open it in the web browser](http://localhost:7070/))

```bash
cd nosql-server
docker-compose up -d
```

#### NoSQL Populate DB:

This tool helps to populate MongoDB with random customers.
This is an ancillary project that can help us to set different scenarios.

- project location: [nosql-populate](nosql-populate)

```bash
./gradlew nosql-populate:install
alias nosql-populate="$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate "
nosql-populate --url "mongodb://root:notasecret@localhost:27017" -d "sandbox" 100
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias nosql-populate='$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate '" >> ~/.zshrc
```

## Kafka Components

#### Kafka Cluster:

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [zookeeper](https://zookeeper.apache.org/)
- project location: [kafka-cluster](kafka-cluster)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`
- kafka version: [2.8](https://docs.confluent.io/platform/current/installation/versions-interoperability.html) (confluent platform 6.2.0)

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
- project location: [kafka-schema-registry](kafka-schema-registry)
- schema registry port: `8081` ([open it in the web browser](http://localhost:8081/))

```bash
cd kafka-schema-registry
docker-compose up -d
```

#### Kafka REST Proxy:

The Kafka REST Proxy provides a RESTful interface to a Kafka cluster.

- [kafka rest](https://docs.confluent.io/platform/current/kafka-rest/index.html)
- [kafka rest api reference](https://docs.confluent.io/platform/current/kafka-rest/api.html)
- project location: [kafka-rest](kafka-rest)
- kafka rest port: `8083` ([open it in the web browser](http://localhost:8083/))

```bash
cd kafka-rest
docker-compose up -d
http :8083/topics
http :8083/topics/test Content-Type:application/vnd.kafka.json.v2+json records:='[{ "key": "test", "value": "test" }]'
http :8083/topics/users Content-Type:application/vnd.kafka.avro.v2+json < kafka-rest-produce-message-avro-payload.json
```

#### Kafka ksqlDB:

ksqlDB is a database that's purpose-built for stream processing applications.

- [ksqldb](https://ksqldb.io/)
- project location: [kafka-ksqldb](kafka-ksqldb)

```bash
cd kafka-ksqldb
docker-compose up -d
kafka-cli-tools ksql http://ksqldb:8088
CREATE STREAM riderLocations (profileId VARCHAR, latitude DOUBLE, longitude DOUBLE) WITH (kafka_topic='locations', value_format='json', partitions=1);
SELECT * FROM riderLocations WHERE GEO_DISTANCE(latitude, longitude, 37.4133, -122.1162) <= 5 EMIT CHANGES;
```

In another terminal:

```bash
kafka-cli-tools ksql http://ksqldb:8088
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('c2309eec', 37.7877, -122.4205);
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('18f4ea86', 37.3903, -122.0643);
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('4ab5cbad', 37.3952, -122.0813);
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('8b6eae59', 37.3944, -122.0813);
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('4a7c7b41', 37.4049, -122.0822);
INSERT INTO riderLocations (profileId, latitude, longitude) VALUES ('4ddad000', 37.7857, -122.4011);
```

#### Kafka Connect:

It makes it simple to quickly define connectors that move large data sets into and out of Kafka.

- [connect](https://docs.confluent.io/current/connect/index.html)
- [connect api reference](https://docs.confluent.io/platform/current/connect/references/restapi.html)
- [jdbc connector plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [mongo connector plugin](https://www.confluent.io/hub/mongodb/kafka-connect-mongodb)
- project location: [kafka-connect](kafka-connect)
- connect port: `8082` ([open it in the web browser](http://localhost:8082/))

```bash
cd kafka-connect
docker-compose up -d
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
http POST :8082/connectors < connectors/mysql-source-create-connector-payload.json
http POST :8082/connectors < connectors/mongo-sink-create-connector-payload.json
```

#### Kafka Clients - Avro Producer and Consumer:

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

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-clients='$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients '" >> ~/.zshrc
```

For creating a AVRO schema, you can use the following command (development purposes):

```bash
./gradlew kafka-clients:generateAvro
```

#### Kafka Clients - Spring Boot:

Spring Boot + Spring Kafka producer and consumer example.

- [spring kafka examples](https://spring.io/projects/spring-kafka)
- project location: [kafka-spring-boot](kafka-spring-boot)
- spring port: `8585` ([open it in the web browser](http://localhost:8585/actuator))

```bash
./gradlew kafka-spring-boot:bootRun
```

In another terminal:

```bash
http :8585/actuator/health
http :8585/produce messages==10
```

