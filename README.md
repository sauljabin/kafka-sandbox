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

- proyect location: [kafka-cluster](kafka-cluster)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`

```bash
cd kafka-cluster
docker-compose up -d
docker-compose down
```

#### Kafka AKHQ:

- proyect location: [kafka-akhq](kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

```bash
cd kafka-akhq
docker-compose up -d
docker-compose down
```

#### Kafka Schema Registry:

- proyect location: [kafka-schema-registry](kafka-schema-registryq)
- schema registry port: `8081` ([open it in the web browser](http://localhost:8081/))
- schema registry ui port: `8000` ([open it in the web browser](http://localhost:8000/))

```bash
cd kafka-schema-registry
docker-compose up -d
docker-compose down
```

## Sandbox Commands

#### List Tasks

```
./gradlew tasks --all
```

#### Create Sandbox Network

```
./gradlew createSandboxNetwork
```

#### Run kafka cluster

```
./gradlew kafka-cluster:composeUp
```

#### Run kafka connect tools

```
./gradlew kafka-connect:composeUp
```

#### Run kafka AKHQ UI

```
./gradlew kafka-akhq:composeUp
```

#### Run kafka Schema Registry

```
./gradlew kafka-schema-registry:composeUp
```

#### Run the whole sandbox

```
./gradlew composeUp
```

## Kafka Connect Commands

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

## Kafka CLI tools

#### Build docker cli tools

```
docker build ./kafka-cli-tools -t kafka-cli-tools:latest
```

#### Run docker cli tools

```
docker run -it --network kafka-sandbox_network kafka-cli-tools:latest
```