package net.catenax.explorer.core.webui.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;

import java.util.List;

@RequiredArgsConstructor
public class DataSearchResultsProvider implements SearchResultsProvider {
    private final ExplorerService explorerService;

    @Override
    @SneakyThrows
    public List<ShellDescriptorResponse.ShellDescriptor> search(String query) {
        final ShellDescriptorResponse response = explorerService.search(query);
        return response.getItems();
    }

    @Override
    public String getSubmodelData(String url) {
        throw new UnsupportedOperationException("This operation is not supported yet.");
    }
}
