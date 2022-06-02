package net.catenax.explorer.core.submodel.twinregistry;

import static org.springframework.http.HttpMethod.GET;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryClient {

  public static final String LOOKUP_SHELLS_URL = "lookup/shells?assetIds=";
  private static final ParameterizedTypeReference<List<String>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
  };
  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  //GET lookup z {key: PartNumber, value: userInput}
  @SneakyThrows
  void lookup(String query, String endpointAddress) {

//    final List<Wrapper> lookupQuery = List.of(Wrapper.builder()
//        .key("PartNumber")
//        .value(query)
//        .build());
    final List<Wrapper> lookupQuery = List.of(new Wrapper("PartNumber", query));
    final Map<String, String> params = Map.of("assetIds", mapper.writeValueAsString(lookupQuery));
    final ResponseEntity<List<String>> result = restTemplate.exchange(endpointAddress + LOOKUP_SHELLS_URL, GET, null, TYPE_REFERENCE, params);
    log.info(result.getBody().toString());
  }

  record Wrapper(String key, String value) {}
}
