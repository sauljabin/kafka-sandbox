package kafka.sandbox;

import com.github.javafaker.Faker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class App {

    private static final Faker faker = new Faker();
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/sandbox";
    private static final String username = "root";
    private static final String password = "notasecret";

    public static void main(String[] args) throws SQLException, IOException {
        int totalCustomers = getTotalCustomers(args);
        System.out.println("Total customers to create: " + totalCustomers);

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

        Dao<Customer, UUID> accountDao = DaoManager.createDao(connectionSource, Customer.class);

        TableUtils.createTableIfNotExists(connectionSource, Customer.class);

        for (int i = 0; i < totalCustomers; i++) {
            Customer customer = createNewCustomer();
            accountDao.create(customer);
            System.out.println(customer);
        }

        connectionSource.close();
    }

    private static Customer createNewCustomer() {
        return Customer.builder()
                .name(faker.name().fullName())
                .address(faker.address().streetAddress())
                .created(new Date())
                .build();
    }

    private static int getTotalCustomers(String[] args) {
        return Arrays.asList(args).stream()
                .mapToInt(arg -> Integer.parseInt(arg))
                .findFirst().orElse(15);
    }
}
