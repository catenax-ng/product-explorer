package net.catenax.explorer.core.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DataAssetRegistryConfiguration {

  @Bean
  @ConditionalOnProperty(value = "registry.dar.provider", havingValue = "mock", matchIfMissing = true)
  DataAssetRegistryProvider dataAssetRegistryProvider() {
    log.info("Mock DAR provider registered");
    return new MockDataAssetRegistryProvider();
  }
}