# Kafka MQTT Proxy

MQTT Proxy enables MQTT clients to use the MQTT 3.1.1 protocol to publish data directly to Apache Kafka.

> ⚠️ This does not convert kafka into a MQTT broker, this aims to provide a simple way to publish/persist IoT data to Kafka.

- [kafka mqtt](https://docs.confluent.io/platform/current/kafka-mqtt/intro.html)
- [kafka mqtt settings](https://docs.confluent.io/platform/current/kafka-mqtt/configuration_options.html)
- project location: [kafka-mqtt](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-mqtt)
- kafka mqtt tcp port: `1884`

Run Kafka MQTT Proxy:

```bash
cd kafka-mqtt
docker compose up -d
```

Publish a message:

```bash
mqtt-cli pub -h kafka-mqtt -p 1884 -t 'house/room/temperature' -m '20C'
```

Consuming the data:

```bash
kafka-cli kafka-console-consumer --from-beginning --group kafka-mqtt.consumer \
                                 --topic kafka-mqtt.temperature  \
                                 --bootstrap-server kafka1:9092 \
                                 --property print.key=true
```

## Docker Compose

```yaml
{{#include ../../kafka-mqtt/docker-compose.yml}}
```