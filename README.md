# kafka-docker-on-mac

For development porpuse

Kafka Tag `2.12-2.2.1`

Zookeeper Tag `3.4`

## Documentation

- [Kafka](https://kafka.apache.org)
- [Zookeeper](https://zookeeper.apache.org)
- [Zookeeper Docker Hub](https://hub.docker.com/_/zookeeper)

## Getting Started

```
$ make run
```

## Default Ports

| Port | Description |
| - | - |
| 2181 | Zookeeper port |
| 9092 | Internal Kafka port |
| 9093 | External Kafka port |

## Docker Stack Examples

- [docker-compose.yml](docker-compose.yml)

## Docker CMD

Default: `CMD ["kafka", "config/server.properties"]`

You could change the properties path adding a volume to `/kafka/config` path.

## Make Commands

#### Builds the docker image
```
$ make build
```

#### Deploys a kafka broker
```
$ make run
```

#### Shows the stack status
```
$ make status
```

#### Stops stack
```
$ make stop
```

## Commands for Kafka

#### Creates a topic
```
$ make create-topic topic=test
```

#### Muestra la lista de tópicos
```
$ make topic-list
```

#### Creates a console producer connection
```
$ make console-producer topic=test
```

#### Creates a console consumer connection
```
$ make console-consumer topic=test
```
