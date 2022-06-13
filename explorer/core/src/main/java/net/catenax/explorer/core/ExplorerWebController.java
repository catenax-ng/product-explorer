package net.catenax.explorer.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerWebController {

    private final String explorerApplicationUrl;
    private final ObjectMapper objectMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("explorerApplicationUrl", explorerApplicationUrl);
        return "index";
    }

    @GetMapping("v1/assets/mockup/{query}")
    public ResponseEntity<JsonNode> retrieve(@PathVariable final String query) throws JsonProcessingException {
        log.info("Querying for Asset by: " + query);
        if ("test".equals(query)) {
            return ResponseEntity.ok(objectMapper.readValue("""
                    [ { 
                       "items": [
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
                       ]
                     }
                    ]
                    """, JsonNode.class));
        }
        return ResponseEntity.ok(objectMapper.readValue("{\"items\": []}", JsonNode.class));
    }
}
