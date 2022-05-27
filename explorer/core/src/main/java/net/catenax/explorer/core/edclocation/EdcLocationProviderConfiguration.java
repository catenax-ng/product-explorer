package net.catenax.explorer.core.edclocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
/**
 *  application properties config:
 *  registry.edc-location.provider=CSV (or if missing registry.edc-location.provider)
 *    Initializes CsvEdcLocationProvider instance
 *
 *  registry.edc-location.filePath
 *    Path can point to file directly for example file://C:/edc-locations.csv
 *    or to resources in jar for example resource://edc-locations.csv
 *
 *    If configuration is not provided resource://edc-locations.csv will be used
 */
class EdcLocationProviderConfiguration {

  @Value("${registry.edc-location.filePath:resource://edc-locations.csv}")
  private String edcLocationFilePath;

  @Bean
  @ConditionalOnProperty(value = "registry.edc-location.provider", havingValue = "csv", matchIfMissing = true)
  EdcLocationProvider edcLocationProvider() {
    return new CsvEdcLocationProvider(edcLocationFilePath);
  }
}
