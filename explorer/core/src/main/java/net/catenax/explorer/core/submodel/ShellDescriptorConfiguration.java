package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.edc.EdcAssetProvider;
import net.catenax.explorer.core.edc.EdcContractService;
import net.catenax.explorer.core.edc.EdcTransferService;
import net.catenax.explorer.core.twinregistry.TwinRegistryAssetProvider;
import net.catenax.explorer.core.twinregistry.TwinRegistryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellDescriptorConfiguration {

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "edc", matchIfMissing = true)
  ShellDescriptorProvider edcAssetProvider(EdcContractService edcContractService,
      EdcTransferService edcTransferService,
      @Value("${consumer.control.plane}") String consumerControlPlaneUrl) {
    return new EdcAssetProvider(edcContractService, edcTransferService, consumerControlPlaneUrl);
  }

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "twin-registry", matchIfMissing = true)
  TwinRegistryAssetProvider shellDescriptorProvider(WebClient webClient) {
    return new TwinRegistryAssetProvider(
        new TwinRegistryClient(webClient)
    );
  }
}
