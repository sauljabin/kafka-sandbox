# Spring Boot

Spring Boot + Spring Kafka producer and consumer examples.

<div class="warning">

Open a terminal inside the sandbox environment:

```bash
docker compose exec cli bash
```

</div>

### Other LInks

- [confluent spring kafka examples](https://www.confluent.io/blog/apache-kafka-spring-boot-application/)
- [spring kafka settings](https://docs.spring.io/spring-kafka/reference/html/)

### Setup

Run spring boot:

```bash
gradle kafka-spring-boot:bootRun
```

### Produce

Spring has the class `KafkaTemplate` that allows you to produce messages.

```java
@Value("${spring.kafka.topic}")
private String topic;

@Autowired
private KafkaTemplate<String, Customer> kafkaTemplate;

public void sendCustomer(Customer customer) {
    log.info("Producing message: {}", customer);
    kafkaTemplate.send(topic, customer.getId().toString(), customer);
}
```

In another terminal:

```bash
http :8585/produce messages==10
```

### Consume

You can use the `KafkaListener` annotation.

```java
@KafkaListener(topics = { "${spring.kafka.topic}" })
public void consume(ConsumerRecord<String, Customer> record) {
    log.info("Customer ID: {}", record.value());
}
```