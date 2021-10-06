**Kafka Sandbox** helps you to deploy a kafka sandbox locally. It intends to be a simple way to get started with kafka and
help you on your learning path. It provides you with a wide variety of tools from the kafka ecosystem and a simple way
to run them all. It also includes a set of tools and tips to make it easier for you to use kafka. It does not include
security since it is not a production system.

## Table of Contents

* [Interesting Links](#interesting-links)
* [Dependencies](#dependencies)
* [Other Utilities](#other-utilities)
* [Get Started](#get-started)
* [Tools](#tools)
  * [Kafka CLI Tools](#kafka-cli-tools)
  * [SQL Database](#sql-database)
  * [SQL Populate Database](#sql-populate-database)
  * [NoSQL Database](#nosql-database)
  * [NoSQL Populate Database](#nosql-populate-database)
  * [MQTT CLI Tools](#mqtt-cli-tools)
  * [MQTT Broker](#mqtt-broker)
  * [Portainer](#portainer)
* [Kafka Components](#kafka-components)
  * [Kafka Cluster](#kafka-cluster)
  * [Kafka AKHQ](#kafka-akhq)
  * [Kafka Schema Registry](#kafka-schema-registry)
  * [Kafka REST Proxy](#kafka-rest-proxy)
  * [Kafka MQTT Proxy](#kafka-mqtt-proxy)
  * [Kafka Connect](#kafka-connect)
    * [Database example](#database-example)
    * [MQTT example](#mqtt-example)
  * [Kafka ksqlDB](#kafka-ksqldb)
    * [Extensions](#extensions)
  * [Kafka Clients](#kafka-clients)
    * [Avro Producer and Consumer](#avro-producer-and-consumer)
    * [Streams](#streams)
    * [Spring Boot](#spring-boot)
* [Ports Table](#ports-table)

## Interesting Links

- [confluent free courses](https://developer.confluent.io/learn-kafka/)
- [confluent docker images references](https://docs.confluent.io/platform/current/installation/docker/image-reference.html)
- [confluent versions interoperability](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)

## Dependencies

- [docker](https://www.docker.com/) - version 20 or higher
- [java](https://www.java.com/en/download/) - version 11 or higher
- [httpie](https://httpie.io/) - rest client
- [jq](https://stedolan.github.io/jq/) - json parser

## Other Utilities

- [curl](https://curl.se/) - command line http client
- [lazydocker](https://github.com/jesseduffield/lazydocker#installation) - docker text user interface
- [kaskade](https://github.com/sauljabin/kaskade#installation-and-usage) - kafka text user interface

## Get Started

Create a docker network:

```bash
docker network create kafka-sandbox_network
```

Run the kafka cluster:

```bash
cd kafka-cluster
docker compose up -d
```

Run AKHQ:

```bash
cd kafka-akhq
docker compose up -d
```

Open AKHQ at [http://localhost:8080/](http://localhost:8080/)

## Tools

### Kafka CLI Tools

It is a collection of tools to interact with kafka cluster through the terminal.

- [kafkacat](https://github.com/edenhill/kafkacat)
- [zoe](https://adevinta.github.io/zoe/)
- [confluent community tools](https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html)
- project location: [kafka-cli](kafka-cli)

> &#x26a0; Run these commands inside the root folder.

Create an alias for `kafka-cli`:

```bash
alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '" >> ~/.zshrc
```

Create the docker image:

```bash
cd kafka-cli
docker build -t kafka-cli:latest .
kafka-cli
```

### SQL Database

Create a MySQL and PostgresSQL instance and a database.

- [mysql](https://hub.docker.com/_/mysql)
- [postgres](https://hub.docker.com/_/postgres)
- [adminer](https://hub.docker.com/_/adminer)
- project location: [sql-database](sql-database)
- postgres port: `5432`
- mysql port: `3306`
- adminer port: `9090` ([open it in the web browser](http://localhost:9090/))

Run MySQL, PostgresSQL and Adminer:

```bash
cd sql-database
docker compose up -d
```

### SQL Populate Database

This tool helps to populate either a MySQL or PostgresSQL database with random customers. This is an ancillary project
that can help us to set different scenarios.

- project location: [sql-populate](sql-populate)

> &#x26a0; Run these commands inside the root folder.

Create an alias for `sql-populate`:

```bash
alias sql-populate="$PWD/sql-populate/build/install/sql-populate/bin/sql-populate "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias sql-populate='$PWD/sql-populate/build/install/sql-populate/bin/sql-populate '" >> ~/.zshrc
```

Install the app:

```bash
./gradlew sql-populate:install
sql-populate
```

Examples:

```bash
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
sql-populate --url "jdbc:postgresql://localhost:5432/sandbox" --user "postgres" --password "notasecret" 100
```

### NoSQL Database

Create a MongoDB instance and a database.

- [mongo](https://hub.docker.com/_/mongo)
- [mongo express](https://hub.docker.com/_/mongo-express)
- project location: [nosql-database](nosql-database)
- mongo port: `27017`
- mongo express port: `7070` ([open it in the web browser](http://localhost:7070/))

Run MongoDB and Mongo Express:

```bash
cd nosql-database
docker compose up -d
```

### NoSQL Populate Database

This tool helps to populate MongoDB with random customers. This is an ancillary project that can help us to set
different scenarios.

- project location: [nosql-populate](nosql-populate)

> &#x26a0; Run these commands inside the root folder.

Create an alias for `nosql-populate`:

```bash
alias nosql-populate="$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias nosql-populate='$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate '" >> ~/.zshrc
```

Install the app:

```bash
./gradlew nosql-populate:install
nosql-populate
```

Example:

```bash
nosql-populate --url "mongodb://root:notasecret@localhost:27017" -d "sandbox" 100
```

### MQTT CLI Tools

MQTT collection of tools to interact with a MQTT broker.

- [mqtt-cli](https://hivemq.github.io/mqtt-cli/)

> &#x26a0; Run these commands inside the root folder.

Create an alias for `mqtt-cli`:

```bash
alias mqtt-cli='docker run --rm -it --network kafka-sandbox_network hivemq/mqtt-cli:latest '
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias mqtt-cli='docker run --rm -it --network kafka-sandbox_network hivemq/mqtt-cli:latest '" >> ~/.zshrc
```

Test the cli:

```bash
mqtt-cli
```

### MQTT Broker

Eclipse Mosquitto is an open source (EPL/EDL licensed) message broker that implements the MQTT protocol versions 5.0,
3.1.1 and 3.1. Mosquitto is lightweight and is suitable for use on all devices from low power single board computers to
full servers.

- [mosquitto](https://mosquitto.org/)
- project location: [mqtt-broker](mqtt-broker)
- mosquitto port: `1883`

Run Mosquitto:

```bash
cd mqtt-broker
docker compose up -d
mqtt-cli test -h mosquitto
```

### Portainer

It's a docker web UI that allows you to manage your docker containers.

- [portainer](https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/)
- project location: [docker-portainer](docker-portainer)
- portainer port: `9000` ([open it in the web browser](http://localhost:9000/))

Run Portainer:

```bash
cd docker-portainer
docker compose up -d
```

Open Portainer at [http://localhost:9000/](http://localhost:9000/)

> &#x26a0; User: `admin` and password: `notasecret`.

## Kafka Components

### Kafka Cluster

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [kafka settings](https://docs.confluent.io/platform/current/installation/configuration/broker-configs.html)
- [zookeeper](https://zookeeper.apache.org/)
- [zookeeper settings](https://docs.confluent.io/platform/current/zookeeper/deployment.html)
- project location: [kafka-cluster](kafka-cluster)
- kafka version: [2.8 (cp 6.2.0)](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`

Run Kafka and Zookeeper:

```bash
cd kafka-cluster
docker compose up -d
kafka-cli kafka-topics --bootstrap-server kafka1:9092 --list
```

### Kafka AKHQ

UI for managing kafka cluster.

- [akhq](https://akhq.io/)
- [akhq settings](https://github.com/tchiotludo/akhq#kafka-cluster-configuration)
- project location: [kafka-akhq](kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

Run AKHQ:

```bash
cd kafka-akhq
docker compose up -d
```

### Kafka Schema Registry

It provides a RESTful interface for storing and retrieving your Avro, JSON Schema, and Protobuf schemas.

- [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html)
- [schema registry settings](https://docs.confluent.io/platform/current/schema-registry/installation/config.html)
- project location: [kafka-schema-registry](kafka-schema-registry)
- schema registry port: `8081`

Run Schema Registry:

```bash
cd kafka-schema-registry
docker compose up -d
http :8081/config
```

### Kafka REST Proxy

The Kafka REST Proxy provides a RESTful interface to a Kafka cluster.

> &#x26a0; Use this when you really need a rest interface since it is usually more complex than using conventional kafka clients.

- [kafka rest](https://docs.confluent.io/platform/current/kafka-rest/index.html)
- [kafka rest settings](https://docs.confluent.io/platform/current/kafka-rest/production-deployment/rest-proxy/config.html)
- [kafka rest api reference](https://docs.confluent.io/platform/current/kafka-rest/api.html)
- project location: [kafka-rest](kafka-rest)
- requests location: [kafka-rest/requests](kafka-rest/requests)
- kafka rest port: `8083`

Run Kafka REST Proxy:

```bash
cd kafka-rest
docker compose up -d
http :8083/brokers
```

Create topics:

```bash
cd kafka-rest
http :8083/topics/kafka-rest.test Content-Type:application/vnd.kafka.json.v2+json records:='[{ "key": "test", "value": "test" }]'
http :8083/topics/kafka-rest.users Content-Type:application/vnd.kafka.avro.v2+json < requests/produce-avro-message.json
```

### Kafka MQTT Proxy

MQTT Proxy enables MQTT clients to use the MQTT 3.1.1 protocol to publish data directly to Apache Kafka.

> &#x26a0; This does not convert kafka into a MQTT broker, this aims to provide a simple way to publish/persist IoT data to Kafka.

- [kafka mqtt](https://docs.confluent.io/platform/current/kafka-mqtt/intro.html)
- [kafka mqtt settings](https://docs.confluent.io/platform/current/kafka-mqtt/configuration_options.html)
- project location: [kafka-mqtt](kafka-mqtt)
- kafka mqtt tcp port: `1884`

Run Kafka MQTT Proxy:

```bash
cd kafka-mqtt
docker compose up -d
```

Publish a message:

```bash
mqtt-cli pub -h kafka-mqtt -p 1884 -t 'house/room/temperature' -m '20C'
```

Cosuming the data:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-mqtt.consumer \
                                 --topic kafka-mqtt.temperature  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true
```

### Kafka Connect

It makes it simple to quickly define connectors that move large data sets into and out of Kafka.

- [connect](https://docs.confluent.io/current/connect/index.html)
- [connect settings](https://docs.confluent.io/platform/current/installation/configuration/connect/index.html)
- [connect api reference](https://docs.confluent.io/platform/current/connect/references/restapi.html)
- [jdbc connector plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [mongo connector plugin](https://www.confluent.io/hub/mongodb/kafka-connect-mongodb)
- project location: [kafka-connect](kafka-connect)
- plugins location: [kafka-connect/plugins](kafka-connect/plugins)
- requests location: [kafka-connect/requests](kafka-connect/requests)
- connect port: `8082`

Run Kafka Connect:

```bash
cd kafka-connect
docker compose up -d
http :8082/connector-plugins
```

#### Database example

> &#x26a0; This example does not support deletion, for that you have to implement tombstone events at the [source](https://debezium.io/documentation/reference/connectors/postgresql.html#postgresql-tombstone-events) and [sink](https://docs.confluent.io/kafka-connect-jdbc/current/sink-connector/index.html#jdbc-sink-delete-mode).

Populate the databases:

```bash
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
```

Create the connectors using the API:

```bash
cd kafka-connect
http :8082/connectors < requests/create-connector-mysql-source.json
http :8082/connectors < requests/create-connector-mongo-sink.json
http :8082/connectors < requests/create-connector-postgres-sink.json
```

For deleting the connectors:

```bash
http DELETE :8082/connectors/postgres-sink
http DELETE :8082/connectors/mongo-sink
http DELETE :8082/connectors/mysql-source
```

#### MQTT example

Subscribe to topics (for debugging purposes):

```bash
mqtt-cli sub -h mosquitto -t 'house/+/brightness'
```

Create a connector using the API:

```bash
cd kafka-connect
http :8082/connectors < requests/create-connector-mqtt-source.json
```

Publish messages:

```bash
mqtt-cli pub -h mosquitto -t 'house/room/brightness' -m '800LM'
mqtt-cli pub -h mosquitto -t 'house/kitchen/brightness' -m '1000LM'
```

Consuming the data:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-connect.brightness_consumer \
                                 --topic kafka-connect.brightness  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true
```

For deleting the connector:

```bash
http DELETE :8082/connectors/mqtt-source
```

### Kafka ksqlDB

ksqlDB is a database that's purpose-built for stream processing applications.

> &#x26a0; ksqlDB it is not a SQL database, it provides an extra layer for implementing kstream, ktable and connectors through a language (ksql) based on sql.

- [ksqldb](https://ksqldb.io/)
- [ksqldb settings](https://docs.ksqldb.io/en/latest/reference/server-configuration/)
- [ksqldb test runner](https://docs.ksqldb.io/en/latest/how-to-guides/test-an-app/)
- project location: [kafka-ksqldb](kafka-ksqldb)
- statements location: [kafka-ksqldb/statements](kafka-ksqldb/statements)
- test location: [kafka-ksqldb/tests](kafka-ksqldb/tests)
- extensions location: [kafka-ksqldb-extensions/extensions](kafka-ksqldb-extensions/extensions)
- ksqldb port: `8088`

Create an alias for `ksqldb-cli`:

> &#x26a0; Run alias commands inside the root folder.

```bash
alias ksqldb-cli="docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias ksqldb-cli='docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest '" >> ~/.zshrc
```

Run ksqlDB:

```bash
cd kafka-ksqldb
docker compose up -d
http :8088/info
ksqldb-cli ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

Test runner:

```bash
ksqldb-cli ksql-test-runner -e extensions/ \
                            -s statements/create-orders.ksql \
                            -i tests/orders-input.json \
                            -o tests/orders-output.json | grep '>>>'
```

Execute statement files:

```bash
ksqldb-cli ksql -f statements/create-orders.ksql http://ksqldb:8088
ksqldb-cli ksql -f statements/insert-orders.ksql http://ksqldb:8088
```

Deleting all orders:

```bash
ksqldb-cli ksql -e "DROP STREAM ORDERSIZES DELETE TOPIC; DROP STREAM ORDERS DELETE TOPIC;" http://ksqldb:8088
```

Interactive ksqlDB shell:

```bash
ksqldb-cli ksql http://ksqldb:8088
SHOW STREAMS;
```

Using the ksqlDB API, list of streams:

```bash
http :8088/ksql ksql="list streams;" | jq '.[].streams[] | [{name: .name, topic: .topic}]'
```

#### Extensions

ksqlDB extensions are pieces of logic for transforming or aggregating events that ksqlDB can't currently express.

- [ksqldb extensions (udf, udtf, udaf)](https://docs.ksqldb.io/en/latest/how-to-guides/create-a-user-defined-function)
- project location: [kafka-ksqldb-extensions](kafka-ksqldb-extensions)

Check the [Kafka ksqlDB](#kafka-ksqldb) section.

For creating the `jar` extension, you can use the following command (development purposes):

```bash
./gradlew kafka-ksqldb-extensions:shadowJar
```

### Kafka Clients

Java examples for producing and consuming messages from Kafka using the
[java kafka client](https://docs.confluent.io/clients-kafka-java/current/overview.html) lib.


#### Avro Producer and Consumer

These examples produce and consume messages from the `supplier` topic. The producer example produces random suppliers.

- [kafka producer and consumer example](https://docs.confluent.io/platform/current/schema-registry/serdes-develop/serdes-avro.html)
- [kafka consumer settings](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html)
- [kafka producer settings](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html)
- project location: [kafka-clients](kafka-clients)

> &#x26a0; Run these commands inside the root folder.

Create an alias for `kafka-clients`:

```bash
alias kafka-clients="$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-clients='$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients '" >> ~/.zshrc
```

Create a topic:

```bash
kafka-cli kafka-topics --create --bootstrap-server kafka1:9092 \
                       --replication-factor 3 \
                       --partitions 3 \
                       --topic kafka-clients.suppliers
```

Install the app:

```bash
./gradlew kafka-clients:install
kafka-clients
```

Run clients:

```bash
kafka-clients producer 100
kafka-clients consumer
```

For creating a AVRO schema, you can use the following command (development purposes):

```bash
./gradlew kafka-clients:generateAvro
```

#### Streams

Kafka Streams is a client library providing organizations with a particularly efficient framework for processing
streaming data. It offers a streamlined method for creating applications and microservices that must process data in
real-time to be effective.

Check the [Kafka Clients - Avro Producer and Consumer](#kafka-clients---avro-producer-and-consumer) section.

- [kafka streams](https://kafka.apache.org/documentation/streams/)
- [kafka streams examples](https://github.com/confluentinc/kafka-streams-examples)
- project location: [kafka-clients](kafka-clients)

Run streams:

```bash
kafka-clients streams
```

Print results:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-streams.consumer \
                                 --topic kafka-streams.supplier_counts_by_country  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true \
                                 --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

#### Spring Boot

Spring Boot + Spring Kafka producer and consumer examples.

- [confluent spring kafka examples](https://www.confluent.io/blog/apache-kafka-spring-boot-application/)
- [spring kafka settings](https://docs.spring.io/spring-kafka/reference/html/)
- project location: [kafka-spring-boot](kafka-spring-boot)
- spring port: `8585`

> &#x26a0; Run these commands inside the root folder.

Run spring boot:

```bash
./gradlew kafka-spring-boot:bootRun
```

In another terminal:

```bash
http :8585/actuator/health
http :8585/produce messages==10
```

## Ports Table

| Service | Dns | Port |
| - | - | - |
| AKHQ | localhost | [8080](http://localhost:8080/) |
| Adminer | localhost | [9090](http://localhost:9090/) |
| Mongo Express | localhost | [7070](http://localhost:7070/) |
| Portainer | localhost | [9000](http://localhost:9000/) |
| - | - | - |
| Portainer Tunnel | portainer | 8000 |
| Portainer Agent | portainer-agent | 9001 |
| - | - | - |
| MySQL | mysql | 3306 |
| MySQL | localhost | 3306 |
| PostgreSQL | postgres | 5432 |
| PostgreSQL | localhost | 5432 |
| MongoDB | mongo | 27017 |
| MongoDB | localhost | 27017 |
| - | - | - |
| Mosquitto | mosquitto | 1883 |
| Mosquitto | localhost | 1883 |
| - | - | - |
| Kafka 1 | kafka1 | 9092 |
| Kafka 1 | localhost | 19093 |
| Kafka 2 | kafka2 | 9092 |
| Kafka 2 | localhost | 29093 |
| Kafka 3 | kafka3 | 9092 |
| Kafka 3 | localhost | 39093 |
| - | - | - |
| Zookeeper 1 | zookeeper1 | 2181 |
| Zookeeper 1 | localhost | 12181 |
| Zookeeper 2 | zookeeper2 | 2181 |
| Zookeeper 2 | localhost | 22181 |
| Zookeeper 3 | zookeeper3 | 2181 |
| Zookeeper 3 | localhost | 32181 |
| - | - | - |
| Schema Registry | schema-registry | 8081 |
| Schema Registry | localhost | 8081 |
| - | - | - |
| Kafka Connect | kafka-connect | 8082 |
| Kafka Connect | localhost | 8082 |
| - | - | - |
| Kafka REST | kafka-rest | 8083 |
| Kafka REST | localhost | 8083 |
| - | - | - |
| Kafka MQTT | kafka-mqtt | 1884 |
| Kafka MQTT | localhost | 1884 |
| - | - | - |
| ksqlDB | ksqldb | 8088 |
| ksqlDB | localhost | 8088 |
| - | - | - |
| Kafka Clients Spring Boot | localhost | 8585 |