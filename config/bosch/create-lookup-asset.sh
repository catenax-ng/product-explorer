#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'http://localhost:38087/api/v1/data/assets' \
              --header 'X-Api-Key: apipassword' \
              --header 'Content-Type: application/json' \
              --data-raw '{
                "asset": {
                  "properties": {
                    "asset:prop:id": "lookup",
                    "asset:prop:name": "lookup endpoint",
                    "asset:prop:contenttype": "application/json",
                    "asset:prop:policy-id": "use-eu"
                  }
                },
                "dataAddress": {
                  "properties": {
                    "endpoint": "http://twin-registry-bosch:4243/lookup/shells",
                    "type": "HttpData",
                    "proxyQueryParams": "true"
                  }
                }
              }'
)


echo $status_code
