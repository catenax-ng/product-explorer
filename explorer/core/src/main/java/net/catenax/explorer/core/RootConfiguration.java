package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenax.explorer.core.exception.handling.RootControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import reactor.netty.http.client.HttpClient;

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
        .clientConnector(new ReactorClientHttpConnector(
            HttpClient.create().followRedirect(true)
        ))
        .uriBuilderFactory(uriBuilderFactory)
        .build();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }
}