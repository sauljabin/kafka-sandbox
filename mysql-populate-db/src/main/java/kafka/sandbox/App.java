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

    public static void main(String[] args) throws SQLException, IOException {
        int totalCustomers = getTotalCustomers(args);
        System.out.println("Total costomers to create: " + totalCustomers);
        Faker faker = new Faker();

        String databaseUrl = "jdbc:mysql://localhost:3306/sandbox";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, "root", "notasecret");

        Dao<Customer, UUID> accountDao = DaoManager.createDao(connectionSource, Customer.class);

        TableUtils.createTableIfNotExists(connectionSource, Customer.class);

        for (int i = 0; i < totalCustomers; i++) {
            Customer customer = Customer.builder()
                    .name(faker.name().fullName())
                    .address(faker.address().streetAddress())
                    .created(new Date())
                    .build();
            accountDao.create(customer);
            System.out.println(customer);
        }

        connectionSource.close();
    }

    private static int getTotalCustomers(String[] args) {
        return Arrays.asList(args).stream()
                .mapToInt(arg -> Integer.parseInt(arg))
                .findFirst().orElse(15);
    }
}
