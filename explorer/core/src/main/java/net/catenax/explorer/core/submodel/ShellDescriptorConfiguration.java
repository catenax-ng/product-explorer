package net.catenax.explorer.core.submodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.edc.EdcAssetProvider;
import net.catenax.explorer.core.edc.EdcContractService;
import net.catenax.explorer.core.edc.EdcTransferService;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryAssetProvider;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellDescriptorConfiguration {

//  @Bean
//  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "twin-registry", matchIfMissing = true)
//  ShellDescriptorProvider twinRegistryAssetProvider(WebClient webClient, ObjectMapper mapper) {
//    return new TwinRegistryAssetProvider(
//        new TwinRegistryClient(webClient, mapper)
//    );
//  }

  @Bean
  @ConditionalOnProperty(value = "edc.shell-provider", havingValue = "edc", matchIfMissing = true)
  ShellDescriptorProvider edcAssetProvider(EdcContractService edcContractService, EdcTransferService edcTransferService) {
    return new EdcAssetProvider(edcContractService, edcTransferService);
  }
}