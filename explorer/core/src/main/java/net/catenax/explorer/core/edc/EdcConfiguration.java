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
  EdcTransferService edcTransferService(EdcClient edcClient) {
    return new EdcTransferService(edcClient);
  }

  @Bean
  EdcContractService edcContractService(EdcClient edcClient) {
    return new EdcContractService(edcClient);
  }

  @Bean
  CallbackController callbackController() {
    return new CallbackController();
  }

}
