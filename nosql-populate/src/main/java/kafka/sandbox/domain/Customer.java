package kafka.sandbox.domain;

import java.util.Date;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private UUID id;
    private String name;
    private String address;
    private Date created;
}
