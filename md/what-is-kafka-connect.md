# What is Kafka Connect?

<iframe width="560" height="315" src="https://www.youtube.com/embed/mdsNGujpsF8"></iframe>

It is worth mentioning that just like Schema Registry, Kafka Connect is an external and independent service to Kafka.

Kafka Connect will be very useful when you want to have constant flow of data from a data store (**source**)
to another (**sink**).

Kafka Connect works with plugin, you can find several connector on [Confluent Hub](https://www.confluent.io/hub/).
In this sandbox we have installed 3 plugins:

- [jdbc connector plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
- [mqtt connector plugin](https://www.confluent.io/hub/confluentinc/kafka-connect-mqtt)

Plugins above can be found at:

```bash
ls kafka-connect/plugins
```