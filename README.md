# Kafka Sandbox

This project helps you to deploy a kafka sandbox locally.
It intends to be a simple way to get started with kafka and help you on your learning path.
It provides you with a wide variety of tools from the kafka ecosystem and a simple way to run them all.
It also includes a set of tools and tips to make it easier for you to use kafka.
It does not include security since it is not a production system.

#### Interesting Links

- [confluent free courses](https://developer.confluent.io/learn-kafka/)
- [confluent docker images references](https://docs.confluent.io/platform/current/installation/docker/image-reference.html)
- [confluent versions interoperability](https://docs.confluent.io/platform/current/installation/versions-interoperability.html)

#### Dependencies

- [docker](https://www.docker.com/)
- [docker compose](https://docs.docker.com/compose/)
- [httpie](https://httpie.io/)
- [java](https://www.java.com/en/download/)

#### Other Utilities

- [curl](https://curl.se/)
- [jq](https://stedolan.github.io/jq/)
- [lazydocker](https://github.com/jesseduffield/lazydocker#installation)

## Get Started

Creating a network and running the kafka cluster:

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
- project location: [kafka-cli](kafka-cli)

```bash
alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '

cd kafka-cli
docker build -t kafka-cli:latest .
kafka-cli
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '" >> ~/.zshrc
```

#### SQL Database:

Create a MySQL and PostgresSQL instance and a database.

- [mysql](https://hub.docker.com/_/mysql)
- [postgres](https://hub.docker.com/_/postgres)
- [adminer](https://hub.docker.com/_/adminer)
- project location: [sql-database](sql-database)
- postgres port: `5432`
- mysql port: `3306`
- adminer port: `9090` ([open it in the web browser](http://localhost:9090/))

```bash
cd sql-database
docker-compose up -d
```

#### SQL Populate DB:

This tool helps to populate either a MySQL or PostgresSQL database with random customers.
This is an ancillary project that can help us to set different scenarios.

- project location: [sql-populate](sql-populate)

```bash
alias sql-populate="$PWD/sql-populate/build/install/sql-populate/bin/sql-populate "

./gradlew sql-populate:install
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
sql-populate --url "jdbc:postgresql://localhost:5432/sandbox" --user "postgres" --password "notasecret" 100
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias sql-populate='$PWD/sql-populate/build/install/sql-populate/bin/sql-populate '" >> ~/.zshrc
```

#### NoSQL Database:

Create a MongoDB instance and a database.

- [mongo](https://hub.docker.com/_/mongo)
- [mongo express](https://hub.docker.com/_/mongo-express)
- project location: [nosql-database](nosql-database)
- mongo port: `27017`
- mongo express port: `7070` ([open it in the web browser](http://localhost:7070/))

```bash
cd nosql-database
docker-compose up -d
```

#### NoSQL Populate DB:

This tool helps to populate MongoDB with random customers.
This is an ancillary project that can help us to set different scenarios.

- project location: [nosql-populate](nosql-populate)

```bash
alias nosql-populate="$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate "

./gradlew nosql-populate:install
nosql-populate --url "mongodb://root:notasecret@localhost:27017" -d "sandbox" 100
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias nosql-populate='$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate '" >> ~/.zshrc
```

#### Portainer

It's a docker web UI that allows you to manage your docker containers.

- [portainer](https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/)
- project location: [docker-portainer](docker-portainer)

```bash
cd docker-portainer
docker-compose up -d
```

## Kafka Components

#### Kafka Cluster:

A three node kafka cluster.

- [kafka](https://kafka.apache.org/)
- [kafka settings](https://docs.confluent.io/platform/current/installation/configuration/broker-configs.html)
- [zookeeper](https://zookeeper.apache.org/)
- [zookeeper settings](https://docs.confluent.io/platform/current/zookeeper/deployment.html)
- project location: [kafka-cluster](kafka-cluster)
- kafka ports: `19093`, `29093`, `39093`
- zookeeper ports: `12181`, `22181`, `32181`
- kafka version: [2.8](https://docs.confluent.io/platform/current/installation/versions-interoperability.html) (confluent platform 6.2.0)

```bash
cd kafka-cluster
docker-compose up -d
kafka-cli kafka-topics --bootstrap-server kafka1:19092 --list
```

#### Kafka AKHQ:

UI for managing kafka cluster.

- [akhq](https://akhq.io/)
- [akhq settings](https://github.com/tchiotludo/akhq#kafka-cluster-configuration)
- project location: [kafka-akhq](kafka-akhq)
- akhq port: `8080` ([open it in the web browser](http://localhost:8080/))

```bash
cd kafka-akhq
docker-compose up -d
```

#### Kafka Schema Registry:

It provides a RESTful interface for storing and retrieving your Avro, JSON Schema, and Protobuf schemas.

- [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html)
- [schema registry settings](https://docs.confluent.io/platform/current/schema-registry/installation/config.html)
- project location: [kafka-schema-registry](kafka-schema-registry)
- schema registry port: `8081` ([open it in the web browser](http://localhost:8081/))

```bash
cd kafka-schema-registry
docker-compose up -d
```

#### Kafka REST Proxy:

The Kafka REST Proxy provides a RESTful interface to a Kafka cluster.

- [kafka rest](https://docs.confluent.io/platform/current/kafka-rest/index.html)
- [kafka rest settings](https://docs.confluent.io/platform/current/kafka-rest/production-deployment/rest-proxy/config.html)
- [kafka rest api reference](https://docs.confluent.io/platform/current/kafka-rest/api.html)
- project location: [kafka-rest](kafka-rest)
- kafka rest port: `8083` ([open it in the web browser](http://localhost:8083/))

```bash
cd kafka-rest
docker-compose up -d
http :8083/topics
http :8083/topics/kafka-rest.test Content-Type:application/vnd.kafka.json.v2+json records:='[{ "key": "test", "value": "test" }]'
http :8083/topics/kafka-rest.users Content-Type:application/vnd.kafka.avro.v2+json < kafka-rest-produce-message-avro-payload.json
```

#### Kafka ksqlDB:

ksqlDB is a database that's purpose-built for stream processing applications.

- [ksqldb](https://ksqldb.io/)
- [ksqldb settings](https://docs.ksqldb.io/en/latest/reference/server-configuration/)
- [ksqldb test runner](https://docs.ksqldb.io/en/latest/how-to-guides/test-an-app/)
- project location: [kafka-ksqldb](kafka-ksqldb)
- statements location: [kafka-ksqldb/statements](kafka-ksqldb/statements)
- test location: [kafka-ksqldb/tests](kafka-ksqldb/tests)
- extensions location: [kafka-ksqldb-extensions/extensions](kafka-ksqldb-extensions/extensions)

```bash
alias ksqldb-cli="docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest "

cd kafka-ksqldb
docker-compose up -d
ksqldb-cli ksql -e "SHOW STREAMS;" http://ksqldb:8088
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias ksqldb-cli='docker run --rm -it --network kafka-sandbox_network --workdir /ksqldb -v $PWD/kafka-ksqldb/tests:/ksqldb/tests -v $PWD/kafka-ksqldb/statements:/ksqldb/statements -v $PWD/kafka-ksqldb-extensions/extensions:/ksqldb/extensions kafka-cli:latest '" >> ~/.zshrc
```

Test runner:

```bash
ksqldb-cli ksql-test-runner -e extensions/ -s statements/create-orders.ksql -i tests/orders-input.json -o tests/orders-output.json | grep '>>>'
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

#### Kafka ksqlDB - Extensions:

ksqlDB is a database that's purpose-built for stream processing applications.

- [ksqldb extensions (udf, udtf, udaf)](https://docs.ksqldb.io/en/latest/how-to-guides/create-a-user-defined-function)
- project location: [kafka-ksqldb-extensions](kafka-ksqldb-extensions)

For creating the `jar` extension, you can use the following command (development purposes):

```bash
./gradlew kafka-ksqldb-extensions:shadowJar
```

#### Kafka Connect:

It makes it simple to quickly define connectors that move large data sets into and out of Kafka.

- [connect](https://docs.confluent.io/current/connect/index.html)
- [connect settings](https://docs.confluent.io/platform/current/installation/configuration/connect/index.html)
- [connect api reference](https://docs.confluent.io/platform/current/connect/references/restapi.html)
- [jdbc connector plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [mongo connector plugin](https://www.confluent.io/hub/mongodb/kafka-connect-mongodb)
- project location: [kafka-connect](kafka-connect)
- connect port: `8082` ([open it in the web browser](http://localhost:8082/))

```bash
cd kafka-connect
docker-compose up -d
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
http :8082/connectors < connectors/mysql-source-create-connector-payload.json
http :8082/connectors < connectors/mongo-sink-create-connector-payload.json
```

#### Kafka Clients - Avro Producer and Consumer:

Java examples for producing and consuming messages from Kafka.
These examples produce and consume messages from the `supplier` topic.
The producer example produces random suppliers.

- [kafka producer and consumer example](https://docs.confluent.io/platform/current/schema-registry/serdes-develop/serdes-avro.html)
- [kafka consumer settings](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html)
- [kafka producer settings](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html)
- project location: [kafka-clients](kafka-clients)

```bash
alias kafka-clients="$PWD/kafka-clients/build/install/kafka-clients/bin/kafka-clients "

kafka-cli kafka-topics --create --bootstrap-server kafka1:19092 --replication-factor 3 --partitions 3 --topic kafka-clients.suppliers
./gradlew kafka-clients:install
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

Spring Boot + Spring Kafka producer and consumer examples.

- [confluent spring kafka examples](https://www.confluent.io/blog/apache-kafka-spring-boot-application/)
- [spring kafka settings](https://docs.spring.io/spring-kafka/reference/html/)
- project location: [kafka-spring-boot](kafka-spring-boot)
- spring port: `8585` ([open it in the web browser](http://localhost:8585/actuator/health))

```bash
./gradlew kafka-spring-boot:bootRun
```

In another terminal:

```bash
http :8585/actuator/health
http :8585/produce messages==10
```

#### Kafka Streams

Kafka Streams is a client library providing organizations with a particularly efficient framework for processing streaming data. 
It offers a streamlined method for creating applications and microservices that must process data in real-time to be effective.

- [kafka streams](https://kafka.apache.org/documentation/streams/)
- [kafka streams examples](https://github.com/confluentinc/kafka-streams-examples)
- - project location: [kafka-streams](kafka-streams)

## Ports Table

| Service | Dns | Port |
| - | - | - |
| AKKQ | localhost | [8080](http://localhost:8080/) |
| Adminer | localhost | [9090](http://localhost:9090/) |
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
| Kafka 1 | kafka1 | 19092 |
| Kafka 2 | kafka2 | 29092 |
| Kafka 3 | kafka3 | 39092 |
| Kafka 1 | localhost | 19093 |
| Kafka 2 | localhost | 29093 |
| Kafka 3 | localhost | 39093 |
| - | - | - |
| Zookeeper 1 | zookeeper1 | 12181 |
| Zookeeper 2 | zookeeper2 | 22181 |
| Zookeeper 3 | zookeeper3 | 32181 |
| Zookeeper 1 | localhost | 12181 |
| Zookeeper 2 | localhost | 22181 |
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
| ksqlDB | ksqldb | 8088 |
| ksqlDB | localhost | 8088 |
| - | - | - |
| Kafka Clients Spring Boot | localhost | 8585 |