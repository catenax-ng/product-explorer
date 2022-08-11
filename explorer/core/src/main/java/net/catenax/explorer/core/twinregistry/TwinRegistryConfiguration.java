package net.catenax.explorer.core.twinregistry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.twinregistry.retriever.AssetRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TwinRegistryConfiguration {

  @Bean
  TwinRegistryController twinRegistryController(TwinRegistryService service) {
    return new TwinRegistryController(service);
  }

  @Bean
  TwinRegistryService twinRegistryService(EdcLocationProvider provider, AssetRetriever assetRetriever, TwinRegistryAssetProvider twinRegistryAssetProvider) {
    return new TwinRegistryService(provider, assetRetriever, twinRegistryAssetProvider);
  }

  @Bean
  ExplorerFutureController explorerFutureController(TwinRegistryService service, ObjectMapper mapper) {
    return new ExplorerFutureController(service, mapper);
  }
}