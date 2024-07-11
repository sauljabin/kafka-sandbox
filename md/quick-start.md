# Quick Start

Clone the repo:

```bash
git clone https://github.com/sauljabin/kafka-sandbox.git
cd kafka-sandbox
```

Run the kafka cluster:

```bash
docker compose up -d
```

Check running services:

```bash
docker compose ps
```

Open the sandbox cli:

```bash
docker compose exec cli bash
```

Open [AKHQ](https://akhq.io/) (a web UI for kafka) at [http://localhost:8080/](http://localhost:8080/).