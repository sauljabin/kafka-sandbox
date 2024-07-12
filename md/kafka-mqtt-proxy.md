# Kafka MQTT Proxy

MQTT Proxy enables MQTT clients to use the MQTT protocol to publish data directly to Apache Kafka.
This does not convert kafka into a MQTT broker, this aims to provide a simple way to publish/persist IoT data to Kafka.

### Setup

Run Kafka MQTT Proxy:

```bash
docker compose --profile proxies up -d
```

### Publish Messages

Create topic:

```bash
kafka-topics --create \
             --bootstrap-server kafka1:9092 \
             --replication-factor 3 \
             --partitions 3 \
             --topic proxy.mqtt
```

Publish using mqtt proxy:

```bash
mosquitto_pub -h kafka-mqtt -p 1884 -t "house/room/temperature" -m "20C"
```

Check the data:

```bash
kafka-console-consumer --from-beginning \
                       --bootstrap-server kafka1:9092 \
                       --group proxy.mqtt \
                       --topic proxy.mqtt  \
                       --property print.key=true
```