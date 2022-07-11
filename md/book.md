# About This Book

This book is power by [mdBook](https://rust-lang.github.io/mdBook/index.html).

GitHub [Repository](https://github.com/sauljabin/kafka-sandbox).

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

## Using Docker

Create docker image:

```bash
docker build -t sauljabin/kafka-sandbox-book:latest -f docker/Dockerfile .
```

Running the book ([open it in the web browser](http://localhost)):

```bash
docker run --name kafka-sandbox-book -d -p 80:80 sauljabin/kafka-sandbox-book:latest
```