# Consuming and Producing

### Produce Messages

<iframe width="560" height="315" src="https://www.youtube.com/embed/I7zm3on_cQQ"></iframe>

```bash
kafka-console-producer --bootstrap-server localhost:19092 \
                       --topic sandbox.test
```

### Consume Messages

<iframe width="560" height="315" src="https://www.youtube.com/embed/Z9g4jMQwog0"></iframe>

```bash
kafka-console-consumer --from-beginning \
                       --bootstrap-server localhost:19092 \
                       --group sandbox.test \
                       --topic sandbox.test
```

It is key to know that kafka stores binary data, it does not care if
the internal serialization of the data represents a character string,
image, number, or even if the data is encrypted or plain.

All the serialization/deserialization process occurs on the client side,
when producing or consuming.
