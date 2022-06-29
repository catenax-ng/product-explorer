#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'http://localhost:38087/api/v1/data/contractdefinitions' \
              --header 'X-Api-Key: apipassword' \
              --header 'Content-Type: application/json' \
              --data-raw '{
                "id": "1",
                "accessPolicyId": "231802-bb34-11ec-8422-0242ac120002",
                "contractPolicyId": "231802-bb34-11ec-8422-0242ac120002",
                "criteria": []
              }
              '
)

echo $status_code