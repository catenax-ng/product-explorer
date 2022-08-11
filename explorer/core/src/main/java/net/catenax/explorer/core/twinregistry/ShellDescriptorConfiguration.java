package net.catenax.explorer.core.twinregistry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellDescriptorConfiguration {

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "extension", matchIfMissing = true)
  TwinRegistryAssetProvider shellDescriptorProvider(WebClient webClient) {
    return new TwinRegistryAssetProvider(
        new TwinRegistryClient(webClient)
    );
  }
}