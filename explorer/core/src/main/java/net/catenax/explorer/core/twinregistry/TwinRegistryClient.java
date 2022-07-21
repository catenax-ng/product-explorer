package net.catenax.explorer.core.twinregistry;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryClient {

  private static final String LOOKUP_SHELLS_URL = "/lookup/shells";
  private static final String FETCH_SHELL_DESCRIPTOR_URL = "/registry/shell-descriptors/fetch";
  private static final ParameterizedTypeReference<List<String>> LIST_STRING_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<ShellDescriptorResponse> SHELL_DESCRIPTOR_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
  };
  private final WebClient webClient;

  @SneakyThrows
  Mono<List<String>> lookup(String query, String endpointAddress) {

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(endpointAddress + LOOKUP_SHELLS_URL)
            .queryParam("assetIds", encode(query, UTF_8))
            .build())
        .retrieve()
        .bodyToMono(LIST_STRING_TYPE_REFERENCE)
        .doOnNext(result -> log.info("Got response from aas: " + result.toString()));
  }


  Flux<ShellDescriptorResponse> fetchShelDescriptor(final String endpointAddress, final Mono<List<String>> matchedSubmodelsIds) {

    return webClient.post()
        .uri(endpointAddress + FETCH_SHELL_DESCRIPTOR_URL)
        .body(matchedSubmodelsIds, LIST_STRING_TYPE_REFERENCE)
        .retrieve()
        .bodyToFlux(SHELL_DESCRIPTOR_TYPE_REFERENCE)
        .doOnNext(result -> log.info("Got shell descriptor: " + result.toPrettyString()));
  }
}