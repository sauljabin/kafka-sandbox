package kafka.sandbox.cli;

import com.github.javafaker.Faker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import kafka.sandbox.domain.Customer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;

@Command(name = "jdbc-populate-db", description = "Easy way of creating customers on a sql db")
public class JdbcPopulateDb implements Callable<Integer> {

    private final Faker faker = new Faker();

    @Option(names = {"-u", "--user"}, description = "User name", required = true)
    private String user;

    @Option(names = {"-p", "--password"}, description = "Passphrase", required = true)
    private String password;

    @Option(names = {"--url"}, description = "Connection URL", required = true)
    private String url;

    @Parameters(index = "0", description = "Total new costumer records to insert (default: ${DEFAULT-VALUE})")
    private int customers = 100;

    private Customer createNewCustomer() {
        return Customer.builder()
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
            System.out.println(customer);
        }

        connectionSource.close();
        return CommandLine.ExitCode.OK;
    }
}
