package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.webui.ExplorerSearchController;
import net.catenax.explorer.core.webui.ExplorerSearchResultsController;
import net.catenax.explorer.core.webui.ExplorerStatusController;
import net.catenax.explorer.core.webui.service.DataSearchResultsProvider;
import net.catenax.explorer.core.webui.service.MockDataSearchResultsProvider;
import net.catenax.explorer.core.webui.service.SearchResultsProvider;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ExplorerConfiguration {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    ExplorerController explorerController(ExplorerService service) {
        return new ExplorerController(service);
    }

    @Bean
    ExplorerSearchController explorerSearchController() {
        return new ExplorerSearchController();
    }

    @Bean
    ExplorerStatusController explorerStatusController() {
        return new ExplorerStatusController();
    }

    @Bean
    ExplorerSearchResultsController explorerSearchResultsController(@Value("${app.use-mockup-data:false}") boolean useMockupData, ObjectMapper objectMapper, ExplorerService explorerService) {
        SearchResultsProvider searchResultsProvider = useMockupData ? new MockDataSearchResultsProvider(objectMapper) : new DataSearchResultsProvider(explorerService);
        return new ExplorerSearchResultsController(searchResultsProvider, objectMapper);
    }

    @Bean
    ExplorerService explorerService(EdcLocationProvider provider, ShellDescriptorProvider shellDescriptorProvider) {
        return new ExplorerService(provider, shellDescriptorProvider);
    }
}
