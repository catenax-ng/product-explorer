#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'http://localhost:8087/api/v1/data/policies' \
              --header 'X-Api-Key: apipassword' \
              --header 'Content-Type: application/json' \
              --data-raw '{
                "uid": "231802-bb34-11ec-8422-0242ac120002",
                "permissions": [
                  {
                    "target": "urn:uuid:a0e5ec34-1e31-4de1-86b1-5e79d7e8164f-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918",
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