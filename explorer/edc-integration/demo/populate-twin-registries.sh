#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

./bmw/init-twin-registry-bmw.sh
./bosch/init-twin-registry-bosch.sh
./daimler/init-twin-registry-daimler.sh

