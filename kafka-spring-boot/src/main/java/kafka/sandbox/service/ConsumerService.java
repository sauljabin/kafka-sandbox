package kafka.sandbox.service;

import kafka.sandbox.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    @KafkaListener(topics = {"${spring.kafka.topic}"})
    public void consume(Customer customer) {
        log.info("Consumed message: {}", customer);
    }

}
