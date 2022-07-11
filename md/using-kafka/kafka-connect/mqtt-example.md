# Kafka Connect MQTT Example

Subscribe to topics (for debugging purposes):

```bash
mqtt-cli sub -h mosquitto -t 'house/+/brightness'
```

Create a connector using the API:

```bash
cd kafka-connect
http :8083/connectors < requests/create-connector-mqtt-source.json
```

Publish messages:

```bash
mqtt-cli pub -h mosquitto -t 'house/room/brightness' -m '800LM'
mqtt-cli pub -h mosquitto -t 'house/kitchen/brightness' -m '1000LM'
```

Consuming the data:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-connect.brightness_consumer \
                                 --topic kafka-connect.brightness  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true
```

For deleting the connector:

```bash
http DELETE :8083/connectors/mqtt-source
```

## Requests

#### requests/create-connector-mqtt-source.json

```json
{{#include ../../../kafka-connect/requests/create-connector-mqtt-source.json}}
```