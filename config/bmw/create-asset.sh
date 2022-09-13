#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'http://localhost:8087/api/v1/data/assets' \
              --header 'X-Api-Key: apipassword' \
              --header 'Content-Type: application/json' \
              --data-raw '{
                "asset": {
                  "properties": {
                    "asset:prop:id": "urn:uuid:a0e5ec34-1e31-4de1-86b1-5e79d7e8164f",
                    "asset:prop:name": "product description",
                    "asset:prop:contenttype": "application/json",
                    "asset:prop:policy-id": "use-eu"
                  }
                },
                "dataAddress": {
                  "properties": {
                    "endpoint": "http://twin-registry-bmw:4243/registry/shell-descriptors/urn:uuid:a0e5ec34-1e31-4de1-86b1-5e79d7e8164f",
                    "type": "HttpData"
                  }
                }
              }'
)

echo $status_code
