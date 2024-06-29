package kafka.sandbox.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    @JsonProperty
    public String id;

    @JsonProperty
    public String firstName;

    @JsonProperty
    public String lastName;

    @JsonProperty
    public String address;

    @JsonProperty
    public int age;
}