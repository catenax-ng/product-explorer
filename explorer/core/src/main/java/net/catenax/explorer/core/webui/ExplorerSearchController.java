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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/discovery")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchController {
    private final SearchResultsProvider searchResultsProvider;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String search(HttpServletResponse response, Model model, @RequestParam("query") String query ) {
        List<SearchResultDto> searchResults = searchResultsProvider.search(query)
                .stream()
                .map(this::mapToSearchResultDto)
                .toList();
        model.addAttribute("discoveryResults", searchResults);
        return "discovery/index";
    }

    @GetMapping("/submodel")
    public String getSubmodelData(Model model, @RequestParam("url") String url) {
        String submodelData = searchResultsProvider.getSubmodelData(url);
        model.addAttribute("submodelData",  submodelData);
        return "discovery/submodel-data";
    }

    @SneakyThrows
    private SearchResultDto mapToSearchResultDto(ShellDescriptorResponse.ShellDescriptor shellDescriptor) {
        return new SearchResultDto(shellDescriptor, objectMapper.writeValueAsString(shellDescriptor));
    }
}
