package net.catenax.explorer.core.extension;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ExtensionConfiguration {

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "twin-registry", matchIfMissing = true)
  DataReferenceProvider dataReferenceProvider(ExtensionClient client) {
    return new ExtensionDataReferenceProvider(client);
  }

  @Bean
  ExtensionClient extensionClient(WebClient webClient, @Value("${edc.extension.url}") String extensionUrl) {
    return new ExtensionClient(webClient, extensionUrl);
  }
}