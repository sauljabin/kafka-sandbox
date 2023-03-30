package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import kafka.sandbox.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Slf4j
@Command(name = "sql-populate", description = "Easy way of creating customers to a sql db")
public class SqlPopulate implements Callable<Integer> {

    private final Faker faker = new Faker();

    @Option(names = { "-u", "--user" }, description = "User name", required = true)
    private String user;

    @Option(names = { "-p", "--password" }, description = "Passphrase", required = true)
    private String password;

    @Option(names = { "--url" }, description = "Connection URL", required = true)
    private String url;

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
        ConnectionSource connectionSource = new JdbcConnectionSource(url, user, password);
        Dao<Customer, UUID> accountDao = DaoManager.createDao(connectionSource, Customer.class);
        TableUtils.createTableIfNotExists(connectionSource, Customer.class);

        for (int i = 0; i < customers; i++) {
            Customer customer = createNewCustomer();
            accountDao.create(customer);
            log.info("Inserted record: {}", customer);
        }

        connectionSource.close();

        return CommandLine.ExitCode.OK;
    }
}
