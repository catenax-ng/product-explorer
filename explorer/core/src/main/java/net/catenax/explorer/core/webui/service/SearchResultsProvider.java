package net.catenax.explorer.core.webui.service;

import net.catenax.explorer.core.webui.dto.SearchResultDto;

import java.util.List;

public interface SearchResultsProvider {
    List<SearchResultDto> search(String query);
}
