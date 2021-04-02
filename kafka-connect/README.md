# Kafka connect sandbox

## Components

- [Kafka](https://hub.docker.com/r/confluentinc/cp-kafka)
- [Zookeeper](https://hub.docker.com/r/confluentinc/cp-zookeeper)
- [Schema Registry](https://hub.docker.com/r/confluentinc/cp-schema-registry)
- [AKHQ](https://github.com/tchiotludo/akhq)  
- [Kafka Connect](https://hub.docker.com/r/confluentinc/cp-kafka-connect)

> Important! when looking for a plugin check `source` plugins vs `sink` plugins.

```
Kafka Connect includes two types of connectors:

Source connector – Ingests entire databases and streams table updates to Kafka topics. 
A source connector can also collect metrics from all your application servers and store 
these in Kafka topics, making the data available for stream processing with low latency.

Sink connector – Delivers data from Kafka topics into secondary indexes such as 
Elasticsearch, or batch systems such as Hadoop for offline analysis.
```

## Documentation

- [Confluent Documentation](https://docs.confluent.io/platform/current/installation/docker/config-reference.html)
- [Docker Examples](https://github.com/confluentinc/examples)
- [Python Examples](https://github.com/confluentinc/confluent-kafka-python)
- [Python Documentation](https://docs.confluent.io/platform/current/clients/confluent-kafka-python/html/index.html)
- [Avro](https://avro.apache.org/docs/current/gettingstartedpython.html)
- [Kafka Connect Documentation](https://docs.confluent.io/platform/current/schema-registry/connect.html)
- [Kafka Connect API](https://docs.confluent.io/platform/current/connect/references/restapi.html)

## MySQL

- [MySQL](https://hub.docker.com/_/mysql)
- [Adminer](https://hub.docker.com/_/adminer)
- [JDBC Plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [JDBC Plugin Documentation](https://docs.confluent.io/kafka-connect-jdbc/current/)
- [JDBC Plugin Settigns](https://docs.confluent.io/kafka-connect-jdbc/current/source-connector/source_config_options.html)

## MongoDB

- [MongoDB](https://hub.docker.com/_/mongo)
- [Mongo Express](https://hub.docker.com/_/mongo-express)
- [MongoDB Plugin](https://www.confluent.io/hub/mongodb/kafka-connect-mongodb)
- [MongoDB Plugin Documentation](https://docs.mongodb.com/kafka-connector/current/)
- [MongoDB Plugin Settigns](https://docs.mongodb.com/kafka-connector/current/kafka-sink-properties/)

## Getting Started

```
$ docker-compose up -d
```

## Default Ports

| Port | Description |
| - | - |
| 2181 | Zookeeper port |
| 9093 | Kafka port |
| 8081 | Schema Registry |
| 8000 | Schema Registry UI|
| 8080 | AKHQ |
| 8082 | Kafka Connect |
| 9000 | Kafka Connect UI|
| 3306 | MySQL |
| 9090 | Adminer |
| 27017 | MongoDB |
| 7070 | Mongo Express |

## Make Commands

#### Deploys a kafka broker
```
$ make up
```

#### Shows the stack status
```
$ make status
```

#### Stops stack
```
$ make down
```

## Running examples using docker

#### Run app for sql transactions
```
$ make run-app-transactions
```

#### Run app for producing events
```
$ make run-app-events-producer
```

#### Run app for comsuming events
```
$ make run-app-events-consumer
```

## Connectors

#### Create Sink the connector
```
$ make create-sink-connector
```

#### Update Sink connector
```
$ make update-sink-connector
```

#### Create Source the connector
```
$ make create-source-connector
```

#### Update Source connector
```
$ make update-source-connector
```

#### List connectors
```
$ make list-connectors
```

#### List plugins
```
$ make list-plugins
```