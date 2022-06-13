package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ExplorerConfiguration {
  @Bean
  ExplorerController explorerController(ExplorerService service) {
    return new ExplorerController(service);
  }

  @Bean
  ExplorerWebController explorerWebController(@Value("${explorer-application-query-url:http://localhost:8080/v1/assets/mockup/}") String explorerApplicationUrl, ObjectMapper objectMapper) {
    return new ExplorerWebController(explorerApplicationUrl, objectMapper);
  }

  @Bean
  ExplorerService explorerService(EdcLocationProvider provider, ShellDescriptorProvider shellDescriptorProvider) {
    return new ExplorerService(provider, shellDescriptorProvider);
  }
}
