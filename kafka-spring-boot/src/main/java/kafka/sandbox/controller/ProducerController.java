package kafka.sandbox.controller;

import com.github.javafaker.Faker;
import kafka.sandbox.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
public class ProducerController {

    private Faker faker;

    @Autowired
    public ProducerController(Faker faker) {
        this.faker = faker;
    }

    @GetMapping("/produce")
    public List<Customer> produce(@RequestParam(defaultValue = "100") int messages) {
        List<Customer> customers = new LinkedList<>();

        for (int i = 0; i < messages; i++) {
            customers.add(newProduct());
        }

        return customers;
    }

    private Customer newProduct() {
        return Customer.builder()
                .id(UUID.randomUUID())
                .name(faker.name().fullName())
                .birthdate(faker.date().birthday(18, 80).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(faker.regexify("M|F"))
                .build();
    }

}
