package net.catenax.explorer.core.submodel.twinregistry;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Duration.ofSeconds;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryClient {

  private static final String LOOKUP_SHELLS_URL = "/lookup/shells";
  private static final String FETCH_SHELL_DESCRIPTOR_URL = "/registry/shell-descriptors/fetch";
  private static final ParameterizedTypeReference<List<String>> LIST_STRING_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<ShellDescriptorResponse> SHELL_DESCRIPTOR_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};
  private final WebClient webClient;
  private final ObjectMapper mapper;

  @SneakyThrows
  List<String> lookup(String query, String endpointAddress) {

    final String param = mapper.writeValueAsString(List.of(new Wrapper("PartNumber", query)));
    final List<String> result = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(endpointAddress + LOOKUP_SHELLS_URL)
            .queryParam("assetIds", encode(param, UTF_8))
            .build())
        .retrieve()
        .bodyToMono(LIST_STRING_TYPE_REFERENCE)
        .block(ofSeconds(5));
    log.info("Got response from aas: " + result);
    return result;
  }

  @SneakyThrows
  ShellDescriptorResponse fetchShellDescriptor(final String endpointAddress, final List<String> matchedSubmodelsIds) {

    final ShellDescriptorResponse result = webClient.post()
        .uri(endpointAddress + FETCH_SHELL_DESCRIPTOR_URL)
        .body(Mono.just(matchedSubmodelsIds), LIST_STRING_TYPE_REFERENCE)
        .retrieve()
        .bodyToMono(SHELL_DESCRIPTOR_TYPE_REFERENCE)
        .block(ofSeconds(5));
    log.info("Got shell descriptor:" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    return result;
  }

  record Wrapper(String key, String value) {

  }
}