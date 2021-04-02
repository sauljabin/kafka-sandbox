projectName=kafka-connect
docker-compose=docker-compose -p $(projectName) --project-directory ./ \
								-f ./docker-compose/kafka.yml \
								-f ./docker-compose/schema-registry.yml \
								-f ./docker-compose/akhq.yml \
								-f ./docker-compose/kafka-connect.yml \
								-f ./docker-compose/mongo.yml \
								-f ./docker-compose/mysql.yml
docker-run=docker run -it --rm --network $(projectName)_kafka_network

up:
	$(docker-compose) up -d $(service)

status:
	$(docker-compose) ps

down:
	$(docker-compose) down

build-app-events:
	docker build -t $(projectName)_app-events ./apps/events

run-app-events-producer: build-app-events
	$(docker-run) -e DOCKER=true -v $(shell pwd)/apps/events:/app $(projectName)_app-events python /app/avro-producer.py

run-app-events-consumer: build-app-events
	$(docker-run) -e DOCKER=true -v $(shell pwd)/apps/events:/app $(projectName)_app-events python /app/avro-consumer.py

build-app-transactions:
	docker build -t $(projectName)_app-transactions ./apps/transactions

run-app-transactions: build-app-transactions
	$(docker-run) -e DOCKER=true -v $(shell pwd)/apps/transactions:/app $(projectName)_app-transactions python /app/insert-records.py

create-sink-connector:
	$(docker-run) -v $(shell pwd)/connectors:/connectors python:3 bash -c "curl -s -X POST http://kafka-connect:8082/connectors -d @/connectors/mongo-sink/create-connector-payload.json --header 'Content-Type: application/json' | python -m json.tool"

update-sink-connector:
	$(docker-run) -v $(shell pwd)/connectors:/connectors python:3 bash -c "curl -s -X PUT http://kafka-connect:8082/connectors/mongo-sink/config -d @/connectors/mongo-sink/update-connector-payload.json --header 'Content-Type: application/json' | python -m json.tool"

create-source-connector:
	$(docker-run) -v $(shell pwd)/connectors:/connectors python:3 bash -c "curl -s -X POST http://kafka-connect:8082/connectors -d @/connectors/mysql-source/create-connector-payload.json --header 'Content-Type: application/json' | python -m json.tool"

update-source-connector:
	$(docker-run) -v $(shell pwd)/connectors:/connectors python:3 bash -c "curl -s -X PUT http://kafka-connect:8082/connectors/mysql-source/config -d @/connectors/mysql-source/update-connector-payload.json --header 'Content-Type: application/json' | python -m json.tool"

list-connectors:
	$(docker-run) python:3 bash -c "curl -s http://kafka-connect:8082/connectors | python -m json.tool"

list-plugins:
	$(docker-run) python:3 bash -c "curl -s http://kafka-connect:8082/connector-plugins | python -m json.tool"