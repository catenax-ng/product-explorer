package net.catenax.explorer.core.submodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryAssetProvider;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellDescriptorConfiguration {

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "twin-registry", matchIfMissing = true)
  ShellDescriptorProvider shellDescriptorProvider(WebClient webClient, ObjectMapper mapper) {
    return new TwinRegistryAssetProvider(
        new TwinRegistryClient(webClient, mapper)
    );
  }
}