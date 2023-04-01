# Kafka CLI Tools

It is a collection of tools to interact with kafka cluster through the terminal.

- [kafkacat](https://github.com/edenhill/kafkacat)
- [confluent community tools](https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html)
- project location: [kafka-cli](https://github.com/sauljabin/kafka-sandbox/tree/main/kafka-cli)

> ⚠️ Run these commands inside the root folder.

Create an alias for `kafka-cli`:

```bash
alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias kafka-cli='docker run --rm -it --network kafka-sandbox_network kafka-cli:latest '" >> ~/.zshrc
```

Create the docker image:

```bash
cd kafka-cli
docker build -t kafka-cli:latest .
kafka-cli
```