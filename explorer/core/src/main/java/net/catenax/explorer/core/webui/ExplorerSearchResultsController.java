package net.catenax.explorer.core.webui;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
import net.catenax.explorer.core.webui.dto.SearchResultDto;
import net.catenax.explorer.core.webui.service.SearchResultsProvider;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchResultsController {
    private final SearchResultsProvider searchResultsProvider;
    private final ObjectMapper objectMapper;

    @GetMapping()
    public String index(Model model) {
        return "search/search-result-page";
    }

    @PostMapping()
    public String search(@RequestParam("query") String query, Model model) {
        List<SearchResultDto> searchResults = searchResultsProvider.search(query)
                .stream()
                .map(this::mapToSearchResultDto)
                .toList();
        model.addAttribute("results", searchResults);
        return "search/search-result-page-data";
    }

    @GetMapping("/submodel")
    public String getSubmodelPage(Model model) {
        return "search/submodel-page";
    }

    @PostMapping("/submodel")
    public String getSubmodelData(Model model, @RequestParam("url") String url) {
        String submodelData = searchResultsProvider.getSubmodelData(url);
        model.addAttribute("submodelData", submodelData);
        return "search/submodel-page-data";
    }

    @SneakyThrows
    private SearchResultDto mapToSearchResultDto(ShellDescriptorResponse.ShellDescriptor shellDescriptor) {
        return new SearchResultDto(shellDescriptor, objectMapper.writeValueAsString(shellDescriptor));
    }
}
