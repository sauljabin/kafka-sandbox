package kafka.sandbox.service;

import kafka.sandbox.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

    // in can receive either a ConsumerRecord or Customer class
    @KafkaListener(topics = { "${spring.kafka.topic}" })
    public void consume(ConsumerRecord<String, Customer> record) {
        log.info(
            "Consumed message: partition = {}, offset = {}, key = {}, value = {}",
            record.partition(),
            record.offset(),
            record.key(),
            record.value()
        );
    }
}
