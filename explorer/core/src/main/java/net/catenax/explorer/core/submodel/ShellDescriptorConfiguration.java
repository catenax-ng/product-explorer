package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.edc.EdcAssetProvider;
import net.catenax.explorer.core.edc.EdcContractService;
import net.catenax.explorer.core.edc.EdcTransferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShellDescriptorConfiguration {

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "edc", matchIfMissing = true)
  ShellDescriptorProvider edcAssetProvider(EdcContractService edcContractService,
      EdcTransferService edcTransferService,
      @Value("${consumer.control.plane}") String consumerControlPlaneUrl) {
    return new EdcAssetProvider(edcContractService, edcTransferService, consumerControlPlaneUrl);
  }
}