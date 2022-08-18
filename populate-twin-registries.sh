#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

./config/bmw/init-twin-registry-bmw.sh
./config/bosch/init-twin-registry-bosch.sh
./config/daimler/init-twin-registry-daimler.sh

