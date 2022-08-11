package net.catenax.explorer.core.twinregistry.retriever;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AssetRetrieverConfiguration {

  @Bean
  @ConditionalOnProperty(value = "asset.retriever", havingValue = "mock", matchIfMissing = true)
  AssetRetriever assetRetriever() {
    log.info("Mock retriever registered");
    return new MockAssetRetriever();
  }
}