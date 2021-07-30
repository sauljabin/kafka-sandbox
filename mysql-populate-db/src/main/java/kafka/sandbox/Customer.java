package kafka.sandbox;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "customers")
public class Customer {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String address;

    @DatabaseField(canBeNull = false)
    private Date created;

}