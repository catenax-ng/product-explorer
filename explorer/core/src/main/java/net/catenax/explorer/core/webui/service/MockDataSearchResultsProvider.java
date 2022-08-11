package net.catenax.explorer.core.webui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class MockDataSearchResultsProvider implements SearchResultsProvider {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Flux<ShellDescriptor> search(String query) {
        Thread.sleep(1000);
        final ShellDescriptor result = objectMapper.readValue("""
                {
                   "description": [
                     {
                       "language": "en",
                       "text": "Polonez 520e Plugin-Hybrid"
                     }
                   ],
                   "globalAssetId": {
                     "value": ["urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002"]
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
                 }
                """, new TypeReference<>() {
        });
        return Flux.just(result);
    }

    @Override
    @SneakyThrows
    public String getSubmodelData(String url) {
        Thread.sleep(1000);
        return """
                    {
                        "performanceIndicator": {
                        "electricCapacityMin": 1.7976931348623155E308,
                                "electricCapacityMax": 1.7976931348623155E308
                    },
                        "minimalStateOfHealth": {
                        "minimalStateOfHealthValue": 1.7976931348623155E308,
                                "specificatorId": "eOMtThyhVNLWUZNRcBaQKxI",
                                "minimalStateOfHealthPhase": "as specified by OEM"
                    },
                        "type": "HVB",
                        "currentStateOfHealth": [{
                            "currentStateOfHealthPhase": "as specified by OEM",
                                "currentStateOfHealthTimestamp": "2022-06-28T12:42:08.351Z",
                                "currentStateOfHealthValue": 1.7976931348623155E308
                         }]
                    }
                """;
    }
}
