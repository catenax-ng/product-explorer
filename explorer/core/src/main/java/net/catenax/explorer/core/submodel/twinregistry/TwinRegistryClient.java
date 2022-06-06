package net.catenax.explorer.core.submodel.twinregistry;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryClient {

  private static final String LOOKUP_SHELLS_URL = "/lookup/shells?assetIds={assetIds}";
  private static final String FETCH_SUBMODEL_URL = "/registry/shell-descriptors/fetch";
  private static final ParameterizedTypeReference<List<String>> LIST_STRING_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<String> STRING_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};
  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  @SneakyThrows
  List<String> lookup(String query, String endpointAddress) {
    final ResponseEntity<List<String>> result = restTemplate.exchange(
        endpointAddress + LOOKUP_SHELLS_URL,
        GET,
        null,
        LIST_STRING_TYPE_REFERENCE,
        Map.of("assetIds", mapper.writeValueAsString(List.of(new Wrapper("PartNumber", query)))));
    log.info("Got response from aas: " + result.getBody().toString());
    return result.getBody();
  }

  @SneakyThrows
  String fetchSubmodels(final String endpointAddress, final List<String> matchedSubmodelsIds) {
    final ResponseEntity<String> result = restTemplate.exchange(
        endpointAddress + FETCH_SUBMODEL_URL,
        POST,
        new HttpEntity<>(matchedSubmodelsIds),
        STRING_TYPE_REFERENCE
    );
    log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getBody()));
    return result.getBody();
  }



  record Wrapper(String key, String value) {}
}