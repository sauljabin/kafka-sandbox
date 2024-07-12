# Kafka Connect MQTT Example

### Setup Broker

Start MQTT server:

```bash
docker compose --profile mqtt up -d
```

In one terminal, subscribe to mqtt topics:

```bash
mosquitto_sub -h mosquitto -t "house/+/brightness"
```

In another terminal, publish messages:

```bash
mosquitto_pub -h mosquitto -t "house/room/brightness" -m "800LM"
mosquitto_pub -h mosquitto -t "house/kitchen/brightness" -m "1000LM"
```

### Create Source Connector

Payload:

```json
{{#include ../kafka-connect/requests/create-connector-mqtt-source.json}}
```

Create a connector using the API:

```bash
http kafka-connect:8083/connectors < kafka-connect/requests/create-connector-mqtt-source.json
```

In one terminal, consume from kafka:

```bash
kafka-console-consumer --from-beginning --group connect.mqtt \
                       --topic connect.brightness  \
                       --bootstrap-server kafka1:9092 \
                       --property print.key=true
```

In another terminal, publish new messages to the MQTT broker:

```bash
mosquitto_pub -h mosquitto -t "house/room/brightness" -m "810LM"
mosquitto_pub -h mosquitto -t "house/kitchen/brightness" -m "1020LM"
```

Deleting the connector:

```bash
http DELETE kafka-connect:8083/connectors/mqtt-source
```
