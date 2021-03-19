docker-compose=docker-compose -p kafka -f docker-compose.yml
bash=docker run -it --rm --network kafka_kafka_network confluentinc/cp-kafka:6.1.0

topic=default
group=default
replication=2
partitions=6

up:
	$(docker-compose) up -d

status:
	$(docker-compose) ps

down:
	$(docker-compose) down

create-topic:
	$(bash) kafka-topics --create --bootstrap-server kafka1:19092 --replication-factor $(replication) --partitions $(partitions) --topic $(topic)

topic-list:
	$(bash) kafka-topics --list --bootstrap-server kafka1:19092

producer:
	$(bash) kafka-console-producer --broker-list kafka1:19092 --topic $(topic)

consumer:
	$(bash) kafka-console-consumer --bootstrap-server kafka1:19092 --topic $(topic) --consumer-property group.id=$(group) --from-beginning

describe-topic:
	$(bash) kafka-topics --describe --topic $(topic) --bootstrap-server kafka1:19092

build-examples:
	docker build -t kafka-examples ./examples

bash-examples: build-examples
	docker run -it --rm --network kafka_kafka_network -e DOCKER=true -v $(shell pwd)/examples:/examples kafka-examples bash
