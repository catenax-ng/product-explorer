package net.catenax.explorer.core;

import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ExplorerConfiguration {

  @Bean
  ExplorerController explorerController(ExplorerService service) {
    return new ExplorerController(service);
  }

  @Bean
  ExplorerService explorerService(EdcLocationProvider provider, ShellDescriptorProvider shellDescriptorProvider) {
    return new ExplorerService(provider, shellDescriptorProvider);
  }
}
