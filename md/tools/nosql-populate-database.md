# NoSQL Populate Database

This tool helps to populate MongoDB with random customers. This is an ancillary project that can help us to set
different scenarios.

- project location: [nosql-populate](https://github.com/sauljabin/kafka-sandbox/tree/main/nosql-populate)

> ⚠️ Run these commands inside the root folder.

Create an alias for `nosql-populate`:

```bash
alias nosql-populate="$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate "
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias nosql-populate='$PWD/nosql-populate/build/install/nosql-populate/bin/nosql-populate '" >> ~/.zshrc
```

Install the app:

```bash
./gradlew nosql-populate:install
nosql-populate
```

Example:

```bash
nosql-populate --url "mongodb://root:notasecret@localhost:27017" -d "sandbox" 100
```