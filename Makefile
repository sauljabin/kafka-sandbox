-include .env
export

docker-compose=docker-compose -p kafka -f docker-compose.yml
bash=docker run -it --rm --entrypoint /bin/bash --network kafka_kafka_network

topic=default
group=default
instance=1
replication=3
partitions=3

build:
	docker build -t kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) --build-arg SCALA_VERSION=$(SCALA_VERSION) --build-arg KAFKA_VERSION=$(KAFKA_VERSION) .

run: build
	$(docker-compose) up -d

status:
	$(docker-compose) ps

stop:
	$(docker-compose) down

log-kafka:
	$(docker-compose) logs -f kafka

log-zookeeper:
	$(docker-compose) logs -f zookeeper

bash-kafka:
	$(docker-compose) exec kafka bash

bash-zookeeper:
	$(docker-compose) exec zookeeper bash

create-topic:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-topics --create --zookeeper zookeeper:2181 --replication-factor $(replication) --partitions $(partitions) --topic $(topic)

topic-list:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION)	kafka-topics --list --zookeeper zookeeper:2181

producer:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-console-producer --broker-list kafka:9092 --topic $(topic)

consumer:
	$(bash) kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) kafka-console-consumer --bootstrap-server kafka:9092 --topic $(topic) --consumer-property group.id=$(group) --from-beginning
