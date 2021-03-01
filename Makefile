-include .env
export

bash=docker run -it --rm --entrypoint /bin/bash --network host

topic=default
group=default
instance=1

build:
	docker build -t kafka:$(SCALA_VERSION)-$(KAFKA_VERSION) --build-arg SCALA_VERSION=$(SCALA_VERSION) --build-arg KAFKA_VERSION=$(KAFKA_VERSION) .

run: build
	docker-compose -f docker-compose.yml up -d

status:
	docker-compose -f docker-compose.yml ps

stop:
	docker-compose -f docker-compose.yml down

log-kafka:
	docker-compose -f docker-compose.yml logs -f kafka

log-zookeeper:
	docker-compose -f docker-compose.yml logs -f zookeeper

bash-kafka:
	$(bash) -v kafka_data:/data -v kafka_logs:/kafka/logs -v kafka_certificates:/certs kafka

bash-zookeeper:
	$(bash) -v zookeeper_data:/data -v zookeeper_datalog:/datalog -v zookeeper_logs:/logs zookeeper:3.4

create-topic:
	$(bash) kafka bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic $(topic)

topic-list:
	$(bash) kafka	bin/kafka-topics.sh --list --zookeeper localhost:2181

console-producer:
	$(bash) kafka bin/kafka-console-producer.sh --broker-list localhost:9093 --topic $(topic)

console-consumer:
	$(bash) kafka bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic $(topic) --from-beginning
