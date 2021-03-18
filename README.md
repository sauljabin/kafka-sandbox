# cp-kafka-docker

Example of kafka cluster using the official image. Thi is for development purpose.

## Documentation

- [Kafka](https://hub.docker.com/r/confluentinc/cp-kafka)
- [Zookeeper](https://hub.docker.com/r/confluentinc/cp-zookeeper)
- [Schema Registry](https://hub.docker.com/r/confluentinc/cp-schema-registry)
- [Schema Registry UI](https://hub.docker.com/r/landoop/schema-registry-ui/)
- [Confluent Documentation](https://docs.confluent.io/platform/current/installation/docker/config-reference.html)
- [Docker Examples](https://github.com/confluentinc/examples)
- [Python Example](https://github.com/confluentinc/confluent-kafka-python)
- [Python Documentation](https://docs.confluent.io/platform/current/clients/confluent-kafka-python/html/index.html)

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

## Docker Stack Examples

- [docker-compose.yml](docker-compose.yml)

## Make Commands

#### Builds the docker image
```
$ make build
```

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

#### Opens a terminal inside kafka
```
$ make bash-kafka[123]
```

#### Opens a terminal inside zookeeper
```
$ make bash-zookeeper[123]
```

#### Shows kafka's logs
```
$ make log-kafka[123]
```

#### Shows zookeeper's logs
```
$ make log-zookeeper[123]
```

#### Shows schema registry's logs
```
$ make log-schema
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
