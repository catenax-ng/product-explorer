package net.catenax.explorer.core.submodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryAssetProvider;
import net.catenax.explorer.core.submodel.twinregistry.TwinRegistryClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SubmodelConfiguration {
  @Bean
//      @ConditionalOnProperty
  SubmodelProvider submodelProvider(RestTemplate restTemplate, ObjectMapper mapper) {
    return new TwinRegistryAssetProvider(
        new TwinRegistryClient(restTemplate, mapper)
    );
  }
}
