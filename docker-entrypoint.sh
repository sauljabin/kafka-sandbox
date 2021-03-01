#!/usr/bin/env bash

set -Eeo pipefail

if [[ "$1" == "--override" ]]; then
    exec "kafka" "config/server.properties" "$@"
else
    exec "$@"
fi
