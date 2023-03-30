package kafka.sandbox.service;

import kafka.sandbox.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public void sendCustomer(Customer customer) {
        log.info("Producing message: {}", customer);
        kafkaTemplate.send(topic, customer.getId().toString(), customer);
    }
}
