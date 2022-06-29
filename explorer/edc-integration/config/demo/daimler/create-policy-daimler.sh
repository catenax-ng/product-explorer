#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'http://localhost:18087/api/v1/data/policies' \
              --header 'X-Api-Key: apipassword' \
              --header 'Content-Type: application/json' \
              --data-raw '{
                "uid": "231802-bb34-11ec-8422-0242ac120002",
                "permissions": [
                  {
                    "target": "urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918",
                    "action": {
                      "type": "USE"
                    },
                    "edctype": "dataspaceconnector:permission"
                  }
                ],
                "@type": {
                  "@policytype": "set"
                }
              }
              '
)

echo $status_code