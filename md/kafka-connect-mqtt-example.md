# Kafka Connect MQTT Example

### Setup Broker

Start MQTT server:

```bash
docker compose --profile mqtt up -d
```

In one terminal, subscribe to mqtt topics:

```bash
mqtt sub -h localhost -t 'house/+/brightness'
```

In another terminal, publish messages:

```bash
mqtt pub -h localhost -t 'house/room/brightness' -m '800LM'
mqtt pub -h localhost -t 'house/kitchen/brightness' -m '1000LM'
```

### Create Source Connector

Payload:

```json
{{#include ../kafka-connect/requests/create-connector-mqtt-source.json}}
```

Create a connector using the API:

```bash
http :8083/connectors < kafka-connect/requests/create-connector-mqtt-source.json
```

In one terminal, consume from kafka:

```bash
kafka-console-consumer --from-beginning --group connect.mqtt \
                       --topic connect.brightness  \
                       --bootstrap-server localhost:19092 \
                       --property print.key=true
```

In another terminal, publish new messages to the MQTT broker:

```bash
mqtt pub -h localhost -t 'house/room/brightness' -m '810LM'
mqtt pub -h localhost -t 'house/kitchen/brightness' -m '1020LM'
```

Deleting the connector:

```bash
http DELETE :8083/connectors/mqtt-source
```
