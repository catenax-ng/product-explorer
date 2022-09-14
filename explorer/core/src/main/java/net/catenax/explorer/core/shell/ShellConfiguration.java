package net.catenax.explorer.core.shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ShellConfiguration {

  @Bean
  ShellDescriptorRetriever shellDescriptorRetriever(WebClient client, ObjectMapper objectMapper) {
    return new ShellDescriptorRetriever(client, objectMapper);
  }
}
