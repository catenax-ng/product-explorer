package net.catenax.explorer.core.webui.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.webui.dto.SearchResultDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RealDataSearchResultsProvider implements SearchResultsProvider {
    private final ExplorerService explorerService;

    @Override
    @SneakyThrows
    public List<SearchResultDto> search(String query) {
        List<SearchResultDto> results = new ArrayList<>();

        final List<String> searchResults = explorerService.search(query);
        // TODO: map search results into SearchResultsDto
        return results;
    }
}
