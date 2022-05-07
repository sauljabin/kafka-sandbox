# Quick Start

Clone the repo:

```bash
git clone https://github.com/sauljabin/kafka-sandbox.git
cd kafka-sandbox
```

Create a docker network:

```bash
docker network create kafka-sandbox_network
```

Run the kafka cluster:

```bash
cd kafka-cluster
docker compose up -d
```

Run AKHQ:

```bash
cd kafka-akhq
docker compose up -d
```

Open AKHQ at [http://localhost:8080/](http://localhost:8080/)