projectName=kafka-multi-broker
docker-compose=docker-compose -p $(projectName) --project-directory ./ \
								-f ./docker-compose/kafka.yml \
								-f ./docker-compose/schema-registry.yml \
								-f ./docker-compose/akhq.yml \
								-f ./docker-compose/zookeeper.yml
docker-run=docker run -it --rm --network $(projectName)_kafka_network

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
	$(docker-run) confluentinc/cp-kafka:6.1.0 kafka-topics --create --bootstrap-server kafka1:19092 --replication-factor $(replication) --partitions $(partitions) --topic $(topic)

topic-list:
	$(docker-run) confluentinc/cp-kafka:6.1.0 kafka-topics --list --bootstrap-server kafka1:19092

producer:
	$(docker-run) confluentinc/cp-kafka:6.1.0 kafka-console-producer --broker-list kafka1:19092 --topic $(topic)

consumer:
	$(docker-run) confluentinc/cp-kafka:6.1.0 kafka-console-consumer --bootstrap-server kafka1:19092 --topic $(topic) --consumer-property group.id=$(group) --from-beginning

describe-topic:
	$(docker-run) confluentinc/cp-kafka:6.1.0 kafka-topics --describe --topic $(topic) --bootstrap-server kafka1:19092

build-examples:
	docker build -t $(projectName)_examples ./examples

docker-run-examples: build-examples
	$(docker-run) -e DOCKER=true -v $(shell pwd)/examples:/examples $(projectName)_examples bash
