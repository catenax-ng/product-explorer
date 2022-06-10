#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

docker-compose -f docker-compose.yaml up -d

sleep 50;

sh examples/1_twin_registries/setup/init-digital-twin-1.sh
sh examples/1_twin_registries/setup/init-digital-twin-2.sh

echo 'created two digial twins'
