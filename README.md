# Kafka Sandbox

This project helps you to deploy a kafka sandbox locally.

## Dependencies

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [httpie](https://httpie.io/)
- [jq](https://stedolan.github.io/jq/)
- [java](https://www.java.com/en/download/)

## Get Started

```bash
docker network create kafka-sandbox_network
cd kafka-cluster
docker-compose up -d
```

## Components

#### Kafka Cluster:

- [kafka](https://kafka.apache.org/)
- project location: [kafka-cluster](kafka-cluster)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`

```bash
cd kafka-cluster
docker-compose up -d
docker-compose down
```

#### Kafka AKHQ:

- [akhq](https://akhq.io/)
- project location: [kafka-akhq](kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

```bash
cd kafka-akhq
docker-compose up -d
docker-compose down
```

#### Kafka Schema Registry:

- [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html)
- project location: [kafka-schema-registry](kafka-schema-registryq)
- schema registry port: `8081` ([open it in the web browser](http://localhost:8081/))
- schema registry ui port: `8000` ([open it in the web browser](http://localhost:8000/))

```bash
cd kafka-schema-registry
docker-compose up -d
docker-compose down
```

#### Kafka CLI tools:

It is a collection of tool to interact with kafka cluster through the terminal.

- [kafkacat](https://github.com/edenhill/kafkacat)
- [zoe](https://adevinta.github.io/zoe/)
- [confluent community tools](https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html)
- project location: [kafka-cli-tools](kafka-cli-tools)

```bash
cd kafka-cli-tools
docker build -t kafka-cli-tools:latest .
docker run -it --network kafka-sandbox_network kafka-cli-tools:latest
```

#### Kafka Connect:

#### Populate MySQL DB

```
./gradlew mysql-populate-db:run --args="100"
```

#### List Kafka Connect Plugins

```
curl -s http://localhost:8082/connector-plugins | jq
```

#### List Kafka Connect Connectors

```
curl -s http://localhost:8082/connectors | jq
```

#### Create mysql-source connector

```
curl -s -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d @./kafka-connect/connectors/mysql-source/create-connector-payload.json http://localhost:8082/connectors | jq
```

#### Update mysql-source connector

```
curl -s -X PUT -H 'Content-Type: application/json' -H 'Accept: application/json' -d @./kafka-connect/connectors/mysql-source/update-connector-payload.json http://localhost:8082/connectors/mysql-source/config | jq
```

#### Create mongodb-sink connector

```
curl -s -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d @./kafka-connect/connectors/mongo-sink/create-connector-payload.json http://localhost:8082/connectors | jq
```

#### Update mongo-sink connector

```
curl -s -X PUT -H 'Content-Type: application/json' -H 'Accept: application/json' -d @./kafka-connect/connectors/mongo-sink/update-connector-payload.json http://localhost:8082/connectors/mongo-sink/config | jq
```

## Open UIs commands

#### Opens Adminer

```
./gradlew openAdminer
```

#### Opens AKHQ

```
./gradlew openAkhq
```

#### Opens Mongo Express

```
./gradlew openMongoExpress
```

#### Opens Schema Registry UI

```
./gradlew openSchemaRegistryUi
```

#### Opens Kafka Connect UI

```
./gradlew openKafkaConnectUi
```

## Producer and Consumer

#### Generate avro schemas

```
./gradlew generateAvro
```

#### Run kafka producer

```
./gradlew kafka-producer:run --args="100"
```

#### Run kafka consumer

```
./gradlew kafka-consumer:run
```

