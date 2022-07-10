# MQTT CLI Tools

MQTT collection of tools to interact with a MQTT broker.

- [mqtt-cli](https://hivemq.github.io/mqtt-cli/)

> ⚠️ Run these commands inside the root folder.

Create an alias for `mqtt-cli`:

```bash
alias mqtt-cli='docker run --rm -it --network kafka-sandbox_network hivemq/mqtt-cli:latest '
```

To permanently add the alias to your shell (`~/.bashrc` or `~/.zshrc` file):

```bash
echo "alias mqtt-cli='docker run --rm -it --network kafka-sandbox_network hivemq/mqtt-cli:latest '" >> ~/.zshrc
```

Test the cli:

```bash
mqtt-cli
```