//package net.catenax.explorer.core.webui.service;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
//
//@RequiredArgsConstructor
//public class MockDataSearchResultsProvider implements SearchResultsProvider {
//    private final ObjectMapper objectMapper;
//
//    @Override
//    @SneakyThrows
//    public List<ShellDescriptorResponse.ShellDescriptor> search(String query) {
//        List<ShellDescriptorResponse.ShellDescriptor> results = new ArrayList<>();
//
//        if ("test".equals(query)) {
//            Thread.sleep(2000);
//            final List<ShellDescriptorResponse> shellDescriptorResponses = objectMapper.readValue("""
//                    [ {
//                       "items": [
//                         {
//                           "description": [
//                             {
//                               "language": "en",
//                               "text": "Polonez 520e Plugin-Hybrid"
//                             }
//                           ],
//                           "globalAssetId": {
//                             "value": ["urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002"]
//                           },
//                           "idShort": "Polonez 520e Plugin-Hybrid",
//                           "identification": "urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002",
//                           "specificAssetIds": [
//                             {
//                               "key": "PartNumber",
//                               "value": "31BK"
//                             },
//                             {
//                               "key": "VAN",
//                               "value": "7e97f714-0785-3f9d-ac37-b31989097be3"
//                             }
//                           ],
//                           "submodelDescriptors": [
//                             {
//                               "description": [
//                                 {
//                                   "language": "en",
//                                   "text": "Traceability Submodel"
//                                 }
//                               ],
//                               "idShort": "traceability",
//                               "identification": "urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918",
//                               "semanticId": {
//                                 "value": [
//                                   "urn:bamm:com.catenax.battery.product_description:1.0.1#ProductDescription"
//                                 ]
//                               },
//                               "endpoints": [
//                                 {
//                                   "interface": "SUBMODEL-1.0RC02",
//                                   "protocolInformation": {
//                                     "endpointAddress": "http://provider-control-plane:8282/some-bpn/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120002-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97918/submodel?content=value&extent=withBlobValue",
//                                     "endpointProtocol": "IDS/ECLIPSE DATASPACE CONNECTOR",
//                                     "endpointProtocolVersion": "0.0.1-SNAPSHOT"
//                                   }
//                                 }
//                               ]
//                             }
//                           ]
//                         }
//                       ]
//                     }
//                    ]
//                    """, new TypeReference<List<ShellDescriptorResponse>>() {
//            });
//            for (ShellDescriptorResponse response : shellDescriptorResponses) {
//                response.getItems().forEach(item -> {
//                    results.add(item);
//                });
//            }
//        }
//        return results;
//    }
//}
