package kafka.sandbox.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private UUID id;
    private String name;
    private LocalDate birthdate;
    private String gender;

    public int getAge() {
        if ((birthdate != null)) {
            return Period.between(birthdate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
