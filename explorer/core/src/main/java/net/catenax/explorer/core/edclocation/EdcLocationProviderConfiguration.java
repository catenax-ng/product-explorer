package net.catenax.explorer.core.edclocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
class EdcLocationProviderConfiguration {

  @Bean
  @ConditionalOnProperty(value = "registry.edc-location.provider", havingValue = "rest", matchIfMissing = true)
  RestEdcLocationProvider restEdcLocationProvider(WebClient webClient) {
    return new RestEdcLocationProvider(webClient);
  }
}
