package net.catenax.explorer.core.webui.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class DataSearchResultsProvider implements SearchResultsProvider {
    private final ExplorerService explorerService;

    @Override
    @SneakyThrows
    public Flux<ShellDescriptor> search(String query) {
        return explorerService.searchEdcs(query);
    }
}
