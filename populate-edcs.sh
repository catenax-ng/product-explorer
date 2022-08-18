#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

./config/bmw/create-asset.sh
./config/bmw/create-contract-definition.sh
./config/bmw/create-policy.sh

./config/bosch/create-asset.sh
./config/bosch/create-contract-definition.sh
./config/bosch/create-policy.sh

./config/daimler/create-asset.sh
./config/daimler/create-contract-definition.sh
./config/daimler/create-policy.sh