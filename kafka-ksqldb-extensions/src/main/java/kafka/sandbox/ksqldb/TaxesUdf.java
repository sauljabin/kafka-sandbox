package kafka.sandbox.ksqldb;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(
    name = "taxes",
    author = "kafka sandbox",
    version = "1.0.0",
    description = "A custom taxes formula for orders."
)
public class TaxesUdf {

    public static final double TAXES = .12;

    @Udf(description = "Calculate taxes.")
    public double taxes(@UdfParameter double amount) {
        return amount * TAXES;
    }
}
