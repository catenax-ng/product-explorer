package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.exception.handling.RootControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
public class RootConfiguration {

  @Bean
  public RootControllerAdvice mainControllerAdvice() {
    return new RootControllerAdvice();
  }

  @Bean
  public WebClient webClient() {
    DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
    uriBuilderFactory.setEncodingMode(EncodingMode.NONE);
    return WebClient.create()
        .mutate()
        .uriBuilderFactory(uriBuilderFactory)
        .build();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }
}