package net.catenax.explorer.core.webui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.webui.dto.SearchResultDto;
import net.catenax.explorer.core.webui.service.SearchResultsProvider;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchController {
    private final SearchResultsProvider searchResultsProvider;
    private final ObjectMapper objectMapper;

    @GetMapping()
    public String index(Model model) {
        return "search-page";
    }

    @PostMapping("/ui/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<SearchResultDto> searchResults = new ArrayList<>();
        searchResultsProvider.search(query).forEach(descriptor -> {
            try {
                searchResults.add(new SearchResultDto(descriptor, objectMapper.writeValueAsString(descriptor)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        model.addAttribute("results", searchResults);
        return "search-page-results";
    }
}
