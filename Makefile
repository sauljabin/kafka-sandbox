-include .env
export

docker-compose=docker-compose -p kafka -f docker-compose.yml
bash=docker run -it --rm --entrypoint /bin/bash --network kafka_kafka_network

topic=default
group=default
replication=3
partitions=3

build:
	docker build -t kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) --build-arg SCALA_VERSION=$(SCALA_VERSION) --build-arg KAFKA_VERSION=$(KAFKA_VERSION) .

up: build
	$(docker-compose) up -d

status:
	$(docker-compose) ps

down:
	$(docker-compose) down

log-kafka1:
	$(docker-compose) logs -f kafka1

log-kafka2:
	$(docker-compose) logs -f kafka2

log-kafka3:
	$(docker-compose) logs -f kafka3

log-zookeeper:
	$(docker-compose) logs -f zookeeper

bash-kafka1:
	$(docker-compose) exec kafka1 bash

bash-kafka2:
	$(docker-compose) exec kafka2 bash

bash-kafka3:
	$(docker-compose) exec kafka3 bash

bash-zookeeper:
	$(docker-compose) exec zookeeper bash

create-topic:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-topics --create --zookeeper zookeeper:2181 --replication-factor $(replication) --partitions $(partitions) --topic $(topic)

topic-list:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION)	kafka-topics --list --zookeeper zookeeper:2181

producer:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-console-producer --broker-list kafka1:9092 --topic $(topic)

consumer:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-console-consumer --bootstrap-server kafka1:9092 --topic $(topic) --consumer-property group.id=$(group) --from-beginning
