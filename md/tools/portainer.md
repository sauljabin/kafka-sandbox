# Portainer

It's a docker web UI that allows you to manage your docker containers.

- [portainer](https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/)
- project location: [docker-portainer](https://github.com/sauljabin/kafka-sandbox/tree/main/docker-portainer)
- portainer port: `9000` ([open it in the web browser](http://localhost:9000/))

Run Portainer:

```bash
cd docker-portainer
docker compose up -d
```

Open Portainer at [http://localhost:9000/](http://localhost:9000/)

> &#x26a0; User: `admin` and password: `notasecret`.

## Docker Compose

```yaml
{{#include ../../docker-portainer/docker-compose.yml}}
```