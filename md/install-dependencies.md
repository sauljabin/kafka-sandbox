# Install Dependencies

### Install Java and Docker

- [docker](https://docs.docker.com/engine/install/)
- [java](https://sdkman.io/jdks)

### REST and JSON tools

- [httpie](https://httpie.io/) - rest client
- [jq](https://stedolan.github.io/jq/) - json parser

```bash
brew install httpie jq
```

### Kubernetes Tools

- [helm](https://helm.sh/docs/intro/install/) - kubernetes charts manager
- [kind](https://kind.sigs.k8s.io/) - local kubernetes cluster
- [kubectl](https://kubernetes.io/docs/reference/kubectl/) - kubernetes client cli

```bash
brew install helm kind kubectl
```

### MQTT tools

- [mqtt-cli](https://hivemq.github.io/mqtt-cli/docs/installation/) - MQTT client cli

```bash
brew install hivemq/mqtt-cli/mqtt-cli
```

### Kafka CLI

```bash
brew install kafka
kafka-topics --version
```

### AVRO tools

- [avro](https://avro.apache.org/project/download/) - avro compiler

```bash
brew install avro-tools
```

### SQL clients

```bash
sudo apt install postgresql-client mysql-client
```