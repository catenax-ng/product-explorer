package net.catenax.explorer.core.webui;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.webui.service.SearchResultsProvider;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchController {
    private final SearchResultsProvider searchResultsProvider;

    @GetMapping()
    public String index(Model model) {
        return "search-page";
    }

    @SneakyThrows
    @PostMapping("/ui/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("results", searchResultsProvider.search(query));
        Thread.sleep(3000);
        return "search-page-results";
    }
}
