# Kafka REST Proxy

The Kafka REST Proxy provides a RESTful interface to a Kafka cluster.

> &#x26a0; Use this when you really need a rest interface since it is usually more complex than using conventional kafka clients.

- [kafka rest](https://docs.confluent.io/platform/current/kafka-rest/index.html)
- [kafka rest settings](https://docs.confluent.io/platform/current/kafka-rest/production-deployment/rest-proxy/config.html)
- [kafka rest api reference](https://docs.confluent.io/platform/current/kafka-rest/api.html)
- project location: [kafka-rest](kafka-rest)
- requests location: [kafka-rest/requests](kafka-rest/requests)
- kafka rest port: `8083`

Run Kafka REST Proxy:

```bash
cd kafka-rest
docker compose up -d
http :8083/brokers
```

Create topics:

```bash
cd kafka-rest
http :8083/topics/kafka-rest.test Content-Type:application/vnd.kafka.json.v2+json records:='[{ "key": "test", "value": "test" }]'
http :8083/topics/kafka-rest.users Content-Type:application/vnd.kafka.avro.v2+json < requests/produce-avro-message.json
```