#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

docker-compose -f docker-compose.yaml up -d

sleep 30;

until [ "$(sh setup/init-digital-twin.sh)" != "201" ]; do
    echo 'waiting for containers to start'
done;

echo 'created aas with globalId: urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002'
