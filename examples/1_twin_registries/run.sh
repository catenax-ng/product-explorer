#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

docker-compose -f docker-compose.yaml up -d

sleep 50;

sh setup/init-digital-twin-1.sh
sh setup/init-digital-twin-2.sh

echo 'created two digial twins'
