package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.exception.handling.RootControllerAdvice;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RootConfiguration {

  @Bean
  public RootControllerAdvice mainControllerAdvice() {
    return new RootControllerAdvice();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }
}