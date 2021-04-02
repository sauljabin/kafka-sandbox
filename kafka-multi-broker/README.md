# Kafka sandbox cluster

## Components

- [Kafka](https://hub.docker.com/r/confluentinc/cp-kafka)
- [Zookeeper](https://hub.docker.com/r/confluentinc/cp-zookeeper)
- [Schema Registry](https://hub.docker.com/r/confluentinc/cp-schema-registry)
- [Schema Registry UI](https://hub.docker.com/r/landoop/schema-registry-ui/)
- [AKHQ](https://github.com/tchiotludo/akhq)  

## Documentation

- [Confluent Documentation](https://docs.confluent.io/platform/current/installation/docker/config-reference.html)
- [Docker Examples](https://github.com/confluentinc/examples)
- [Python Examples](https://github.com/confluentinc/confluent-kafka-python)
- [Python Documentation](https://docs.confluent.io/platform/current/clients/confluent-kafka-python/html/index.html)
- [Avro](https://avro.apache.org/docs/current/gettingstartedpython.html)

## Getting Started

```
$ make up
```

## Default Ports

| Port | Description |
| - | - |
| 12181 | Zookeeper port |
| 19093 | Kafka port |
| 8081 | Schema Registry |
| 8000 | Schema Registry UI |
| 8080 | AKHQ |

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

## Commands for Kafka

#### Creates a topic
```
$ make create-topic topic=test
```

#### Shows the topic list
```
$ make topic-list
```

#### Creates a console producer connection
```
$ make producer topic=test
```

#### Creates a console consumer connection
```
$ make consumer topic=test
```

#### Describes a topic
```
$ make describe topic=test
```

## Running examples using docker

#### Opens a python bash 
```
$ make bash-examples
```