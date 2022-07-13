package net.catenax.explorer.core;

import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ExplorerApplication {


  @Bean
  public SpringDocConfiguration springDocConfiguration(){
    return new SpringDocConfiguration();
  }

  @Bean
  public SpringDocConfigProperties springDocConfigProperties() {
    return new SpringDocConfigProperties();
  }

  @Bean
  public ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties){
    //This bean is required because of this issue: https://github.com/springdoc/springdoc-openapi/issues/1736
    return new ObjectMapperProvider(springDocConfigProperties);
  }

  public static void main(String[] args) {
    SpringApplication.run(ExplorerApplication.class, args);
  }

}