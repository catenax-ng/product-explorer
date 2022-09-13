#!/bin/bash

set -o errexit
set -o errtrace
set -o pipefail
set -o nounset

curl --location --request POST 'localhost:4244/registry/shell-descriptors' \
--header 'Content-Type: application/json' \
--write-out %{http_code} --silent --output /dev/null \
--data-raw '{
    "description": [
        {
            "language": "en",
            "text": "BMW 520e Plugin-Hybrid"
        }
    ],
    "globalAssetId": {
        "value": [
            "urn:uuid:a0e5ec34-1e31-4de1-86b1-5e79d7e8164f"
        ]
    },
    "idShort": "BMW 520e Plugin-Hybrid",
    "identification": "urn:uuid:a0e5ec34-1e31-4de1-86b1-5e79d7e8164f",
    "specificAssetIds": [
        {
            "key": "PartNumber",
            "value": "31BK"
        },
        {
            "key": "VAN",
            "value": "85696fe3-00f5-41e2-aca9-3955928fc5ab"
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