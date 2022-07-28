#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

docker-compose -f docker-compose.yaml up -d

sleep 50;

sh setup/init-digital-twin-bmw.sh
sh setup/init-digital-twin-daimler.sh

echo 'created two digial twins'