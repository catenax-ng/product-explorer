package net.catenax.explorer.core.webui.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RealDataSearchResultsProvider implements SearchResultsProvider {
    private final ExplorerService explorerService;

    @Override
    @SneakyThrows
    public List<ShellDescriptorResponse.ShellDescriptor> search(String query) {
        List<ShellDescriptorResponse.ShellDescriptor> results = new ArrayList<>();

        final List<String> searchResults = explorerService.search(query);
        // TODO: map search results into ShellDescriptorResponse
        return results;
    }
}
