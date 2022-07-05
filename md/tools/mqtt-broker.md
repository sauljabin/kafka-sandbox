# MQTT Broker

Eclipse Mosquitto is an open source (EPL/EDL licensed) message broker that implements the MQTT protocol versions 5.0,
3.1.1 and 3.1. Mosquitto is lightweight and is suitable for use on all devices from low power single board computers to
full servers.

- [mosquitto](https://mosquitto.org/)
- project location: [mqtt-broker](https://github.com/sauljabin/kafka-sandbox/tree/main/mqtt-broker)
- mosquitto port: `1883`

Run Mosquitto:

```bash
cd mqtt-broker
docker compose up -d
mqtt-cli test -h mosquitto
```

## Docker Compose

```yaml
{{#include ../../mqtt-broker/docker-compose.yml}}
```