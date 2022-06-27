package net.catenax.explorer.core.webui.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

import java.util.List;

@RequiredArgsConstructor
public class RealDataSearchResultsProvider implements SearchResultsProvider {
    private final ExplorerService explorerService;

    @Override
    @SneakyThrows
    public List<ShellDescriptorResponse.ShellDescriptor> search(String query) {
        final ShellDescriptorResponse response = explorerService.search(query);
        return response.getItems();
    }
}
