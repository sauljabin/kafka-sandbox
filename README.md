# Collection of kafka examples on local envs

Example of kafka cluster using the official image. This is for development purpose.

## TO DO

- [ ] ksqldb
- [ ] proxy
- [ ] streams
- [ ] consumer
- [ ] ssl
- [ ] utils
- [ ] acls

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