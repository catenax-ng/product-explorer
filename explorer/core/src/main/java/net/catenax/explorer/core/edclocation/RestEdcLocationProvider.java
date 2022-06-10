package net.catenax.explorer.core.edclocation;

import static java.time.Duration.ofSeconds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class RestEdcLocationProvider implements EdcLocationProvider {

  @Value("${self.description.hub.url}")
  private String selfDescriptionHubUrl;

  private static final ParameterizedTypeReference<List<String>> LIST_STRING_TYPE_REFERENCE =
      new ParameterizedTypeReference<>() {};

  private static final String SELF_DESCRIPTION_HUB_ROOT_PATH = "/self-desc-hub/";

  private final WebClient webClient;

  @Override
  public List<EdcLocation> getKnownEdcLocations() {
    final List<String> result = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(selfDescriptionHubUrl + SELF_DESCRIPTION_HUB_ROOT_PATH)
            .build())
        .retrieve()
        .bodyToMono(LIST_STRING_TYPE_REFERENCE)
        .block(ofSeconds(5));

    return result == null ? new ArrayList<>()
        : result.stream().map(location -> EdcLocation.builder().url(location).build()).collect(
            Collectors.toList());
  }
}
