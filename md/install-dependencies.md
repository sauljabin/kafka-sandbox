# Install Dependencies

- [docker](https://docs.docker.com/engine/install/)
- [java](https://sdkman.io/jdks)

Extras tools that you are going to use for some examples:

- [httpie](https://httpie.io/) - rest client
- [jq](https://stedolan.github.io/jq/) - json parser
- [helm](https://helm.sh/docs/intro/install/) - kubernetes charts manager
- [kind](https://kind.sigs.k8s.io/) - local kubernetes cluster
- [kubectl](https://kubernetes.io/docs/reference/kubectl/) - kubernetes client cli

```bash
brew install httpie jq helm kind kubectl
```

A collection of kafka client cli tools to manage kafka:

```bash
brew install kafka
kafka-topics --version
```