package kafka.sandbox.controller;

import com.github.javafaker.Faker;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import kafka.sandbox.domain.Customer;
import kafka.sandbox.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private Faker faker;
    private ProducerService producerService;

    @Autowired
    public ProducerController(Faker faker, ProducerService producerService) {
        this.faker = faker;
        this.producerService = producerService;
    }

    @GetMapping("/produce")
    public List<Customer> produce(@RequestParam(defaultValue = "100") int messages) {
        List<Customer> customers = new LinkedList<>();

        for (int i = 0; i < messages; i++) {
            Customer customer = newCustomer();
            customers.add(customer);
            producerService.sendCustomer(customer);
        }

        return customers;
    }

    private Customer newCustomer() {
        return Customer
            .builder()
            .id(UUID.randomUUID())
            .name(faker.name().fullName())
            .birthdate(
                faker
                    .date()
                    .birthday(18, 80)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            )
            .gender(faker.regexify("M|F"))
            .build();
    }
}
