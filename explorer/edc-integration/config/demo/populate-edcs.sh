#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

./bmw/create-asset.sh
./bmw/create-contract-definition.sh
./bmw/create-policy.sh

./bosch/create-asset.sh
./bosch/create-contract-definition.sh
./bosch/create-policy.sh

./daimler/create-asset.sh
./daimler/create-contract-definition.sh
./daimler/create-policy.sh