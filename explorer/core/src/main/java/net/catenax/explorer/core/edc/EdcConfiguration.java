package net.catenax.explorer.core.edc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EdcConfiguration {

  @Bean
  EdcClient edcClient(WebClient webClient) {
    return new EdcClient(webClient);
  }

  @Bean
  EdcContractService edcContractService(EdcClient edcClient) {
    return new EdcContractService(edcClient);
  }

}
