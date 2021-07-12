# Collection of kafka examples on local envs

Example of kafka cluster using the official image. This is for development purpose.


## TO DO

- [ ] ksqldb
- [ ] proxy
- [ ] streams
- [ ] consumer
- [ ] producer
- [ ] ssl
- [ ] utils
- [ ] acls

## Sandbox Commands

#### List Tasks

```
gradle tasks --all
```


#### Create Sandbox Network

```
gradle createSandboxNetwork
```

#### Run kafka cluster

```
gradle kafka-cluster:composeUp
```

#### Run kafka connect tools

```
gradle kafka-connect:composeUp
```

#### Run kafka AKHQ UI

```
gradle kafka-akhq:composeUp
```

#### Run kafka Schema Registry

```
gradle kafka-schema-registry:composeUp
```

#### Run the whole sandbox

```
gradle composeUp
```

## Kafka Connect Commands

#### Populate MySQL DB

```
gradle mysql-populate-db:run --args="100"
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
gradle openAdminer
```

#### Opens AKHQ

```
gradle openAkhq
```

#### Opens Mongo Express

```
gradle openMongoExpress
```

#### Opens Schema Registry UI

```
gradle openSchemaRegistryUi
```

#### Opens Kafka Connect UI

```
gradle openKafkaConnectUi
```