package net.catenax.explorer.core.shell;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellConfiguration {

  @Bean
  ShellDescriptorRetriever shellDescriptorRetriever(WebClient client) {
    return new ShellDescriptorRetriever(client);
  }

  @Bean
  ShellDescriptorLookupRetriever shellDescriptorLookupRetriever(WebClient client) {
    return new ShellDescriptorLookupRetriever(client);
  }
}
