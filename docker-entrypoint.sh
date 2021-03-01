#!/usr/bin/env bash

set -Eeo pipefail

if [[ "$1" == "--override" ]]; then
    exec "kafka-server-start" "$KAFKA_CONFIG/server.properties" "$@"
else
    exec "$@"
fi
