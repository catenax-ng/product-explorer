package net.catenax.explorer.core.webui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchController {
    @GetMapping
    public String index(Model model) {
        return "search/search-page";
    }
}
