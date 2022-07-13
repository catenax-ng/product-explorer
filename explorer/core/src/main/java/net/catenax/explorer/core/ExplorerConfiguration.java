package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.webui.ExplorerSearchController;
import net.catenax.explorer.core.webui.service.DataSearchResultsProvider;
import net.catenax.explorer.core.webui.service.MockDataSearchResultsProvider;
import net.catenax.explorer.core.webui.service.SearchResultsProvider;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
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
  ExplorerSearchController explorerWebController(@Value("${app.use-mockup-data:false}") boolean useMockupData, ObjectMapper objectMapper, ExplorerService explorerService) {
    SearchResultsProvider searchResultsProvider = useMockupData ? new MockDataSearchResultsProvider(objectMapper) : new DataSearchResultsProvider(explorerService);
    return new ExplorerSearchController(searchResultsProvider, objectMapper);
  }

  @Bean
  ExplorerService explorerService(EdcLocationProvider provider, ShellDescriptorProvider shellDescriptorProvider) {
    return new ExplorerService(provider, shellDescriptorProvider);
  }

}
