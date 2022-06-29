package net.catenax.explorer.core.edclocation;

import static java.time.Duration.ofSeconds;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.model.SelfDescription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class RestEdcLocationProvider implements EdcLocationProvider {

  @Value("${self.description.hub.url}")
  private String selfDescriptionHubUrl;

  private static final ParameterizedTypeReference<List<SelfDescription>> LIST_SELF_DESCRIPTION_TYPE_REFERENCE =
      new ParameterizedTypeReference<>() {};

  private final WebClient webClient;

  @Override
  public List<SelfDescription> getKnownEdcLocations() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(selfDescriptionHubUrl)
            .build())
        .retrieve()
        .bodyToMono(LIST_SELF_DESCRIPTION_TYPE_REFERENCE)
        .block(ofSeconds(10));
  }
}
