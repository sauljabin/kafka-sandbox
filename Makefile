-include .env
export

docker-compose=docker-compose -p kafka -f docker-compose.yml
bash=docker run -it --rm --entrypoint /bin/bash --network kafka_network

topic=default
group=default
instance=1

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
	$(bash) -v kafka_kafka_data:/data -v kafka_kafka_logs:/kafka/logs kafka:$(SCALA_VERSION)-$(KAFKA_VERSION)

bash-zookeeper:
	$(bash) -v zookeeper_data:/data -v zookeeper_datalog:/datalog -v zookeeper_logs:/logs zookeeper:$(ZOOKEEPER_VERSION)

create-topic:
	$(bash) kafka bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic $(topic)

topic-list:
	$(bash) kafka	bin/kafka-topics.sh --list --zookeeper zookeeper:2181

console-producer:
	$(bash) kafka bin/kafka-console-producer.sh --broker-list kafka:9093 --topic $(topic)

console-consumer:
	$(bash) kafka bin/kafka-console-consumer.sh --bootstrap-server kafka:9093 --topic $(topic) --from-beginning
