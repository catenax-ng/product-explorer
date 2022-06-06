#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

status_code=$(curl --location --request POST 'localhost:4243/registry/shell-descriptors' \
--header 'Content-Type: application/json' \
--write-out %{http_code} --silent --output /dev/null \
--data-raw '{
    "description": [
        {
            "language": "en",
            "text": "Polonez 520e Plugin-Hybrid"
        }
    ],
    "globalAssetId": {
        "value": [
            "urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002"
        ]
    },
    "idShort": "Polonez 520e Plugin-Hybrid",
    "identification": "urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002",
    "specificAssetIds": [
        {
            "key": "PartNumber",
            "value": "31BK"
        },
        {
            "key": "VAN",
            "value": "7e97f714-0785-3f9d-ac37-b31989097be3"
        }
    ],
    "submodelDescriptors": [
        {
            "description": [
                {
                    "language": "en",
                    "text": "Traceability Submodel"
                }
            ],
            "idShort": "traceability",
            "identification": "urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918",
            "semanticId": {
                "value": [
                    "urn:bamm:com.catenax.battery.product_description:1.0.1#ProductDescription"
                ]
            },
            "endpoints": [
                {
                    "interface": "SUBMODEL-1.0RC02",
                    "protocolInformation": {
                        "endpointAddress": "http://provider-control-plane:8282/some-bpn/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918/submodel?content=value&extent=withBlobValue",
                        "endpointProtocol": "IDS/ECLIPSE DATASPACE CONNECTOR",
                        "endpointProtocolVersion": "0.0.1-SNAPSHOT"
                    }
                }
            ]
        }
    ]
}'
)

echo $status_code