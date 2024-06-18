[Kafka Sandbox](https://sauljabin.github.io/kafka-sandbox/) it's a markdown book designed to help you to deploy a kafka sandbox locally.
It intends to be a simple way to get started with kafka and
help you on your learning path. It provides you with a wide variety of tools from the kafka ecosystem and a simple way
to run them all. It also includes a set of tools and tips to make it easier for you to use kafka. It does not include
security since it is not a production system. 

You can access it at https://sauljabin.github.io/kafka-sandbox/.

> This repository is for educational purposes. This book is power by [mdBook](https://rust-lang.github.io/mdBook/index.html).

## Developing Commands

> You must install [rust](https://www.rust-lang.org/tools/install) first.

Install `mdbook`:

```bash
cargo install mdbook
```

Run local server:

```bash
mdbook serve --open
```

Build statics:

```bash
mdbook build
```
