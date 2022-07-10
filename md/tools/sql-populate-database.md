# SQL Populate Database

This tool helps to populate either a MySQL or PostgresSQL database with random customers. This is an ancillary project
that can help us to set different scenarios.

- project location: [sql-populate](https://github.com/sauljabin/kafka-sandbox/tree/main/sql-populate)

> ⚠️ Run these commands inside the root folder.

Create an alias for `sql-populate`:

```bash
alias sql-populate="$PWD/sql-populate/build/install/sql-populate/bin/sql-populate "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias sql-populate='$PWD/sql-populate/build/install/sql-populate/bin/sql-populate '" >> ~/.zshrc
```

Install the app:

```bash
./gradlew sql-populate:install
sql-populate
```

Examples:

```bash
sql-populate --url "jdbc:mysql://localhost:3306/sandbox" --user "root" --password "notasecret" 100
sql-populate --url "jdbc:postgresql://localhost:5432/sandbox" --user "postgres" --password "notasecret" 100
```