package net.catenax.explorer.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.extension.DataReferenceProvider;
import net.catenax.explorer.core.shell.ShellDescriptorLookupRetriever;
import net.catenax.explorer.core.shell.ShellDescriptorRetriever;
import net.catenax.explorer.core.webui.ExplorerSearchController;
import net.catenax.explorer.core.webui.ExplorerSearchResultsController;
import net.catenax.explorer.core.webui.ExplorerStatusController;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

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
    ExplorerService explorerService(@Value("${app.lookup-asset-name:lookup}") String searchAssetName, EdcLocationProvider locationProvider, DataReferenceProvider dataReferenceProvider, ShellDescriptorRetriever shellDescriptorRetriever, ShellDescriptorLookupRetriever shellDescriptorLookupRetriever, ObjectMapper objectMapper) {
        return new ExplorerService(locationProvider, dataReferenceProvider, shellDescriptorRetriever, shellDescriptorLookupRetriever, objectMapper, searchAssetName);
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
    ExplorerSearchResultsController explorerSearchResultsController(ExplorerService explorerService,
                                                                    SpringTemplateEngine templateEngine) {
        return new ExplorerSearchResultsController(explorerService, templateEngine);
    }
}
