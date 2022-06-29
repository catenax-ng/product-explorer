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
                    "asset:prop:id": "urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918",
                    "asset:prop:name": "product description",
                    "asset:prop:contenttype": "application/json",
                    "asset:prop:policy-id": "use-eu"
                  }
                },
                "dataAddress": {
                  "properties": {
                    "endpoint": "http://twin-registry-bmw:4243/registry/shell-descriptors/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002",
                    "type": "HttpData"
                  }
                }
              }'
)

echo $status_code