package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.concurrent.Callable;
import kafka.sandbox.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Slf4j
@Command(name = "nosql-populate", description = "Easy way of creating customers to a sql db")
public class NoSqlPopulate implements Callable<Integer> {

    private final Faker faker = new Faker();

    @Option(names = { "--url" }, description = "Connection URL", required = true)
    private String url;

    @Option(
        names = { "-d", "--database" },
        description = "DB name (default: ${DEFAULT-VALUE})",
        defaultValue = "sandbox"
    )
    private String database;

    @Parameters(
        index = "0",
        description = "Total new costumer records to insert (default: ${DEFAULT-VALUE})",
        defaultValue = "100"
    )
    private int customers;

    private Customer createNewCustomer() {
        return Customer
            .builder()
            .name(faker.name().fullName())
            .address(faker.address().streetAddress())
            .created(new Date())
            .build();
    }

    @Override
    public Integer call() throws Exception {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings
            .builder()
            .codecRegistry(pojoCodecRegistry)
            .applyConnectionString(new ConnectionString(url))
            .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);

        MongoCollection<Customer> customersCollection = mongoDatabase.getCollection(
            "customers",
            Customer.class
        );

        for (int i = 0; i < customers; i++) {
            Customer customer = createNewCustomer();
            customersCollection.insertOne(customer);
            log.info("Inserted record: {}", customer);
        }

        mongoClient.close();

        return CommandLine.ExitCode.OK;
    }
}
