# Spring Boot

Spring Boot + Spring Kafka producer and consumer examples.

- [confluent spring kafka examples](https://www.confluent.io/blog/apache-kafka-spring-boot-application/)
- [spring kafka settings](https://docs.spring.io/spring-kafka/reference/html/)
- project location: [kafka-spring-boot](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-spring-boot)
- spring port: `8585`

> ⚠️ Run these commands inside the root folder.

Run spring boot:

```bash
./gradlew kafka-spring-boot:bootRun
```

In another terminal:

```bash
http :8585/actuator/health
http :8585/produce messages==10
```