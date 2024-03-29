package net.catenax.explorer.core.webui;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.ExplorerService;
import net.catenax.explorer.core.QueryCommand;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Flux;

import java.net.URLEncoder;

@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class ExplorerSearchResultsController {

    private final ExplorerService explorerService;
    private final SpringTemplateEngine templateEngine;

    @GetMapping
    @SneakyThrows
    public String index(@RequestParam("key") String key, @RequestParam("query") String query, Model model) {
        final String encodedQuery = URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20");
        final String encodedKey = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
        model.addAttribute("query", query);
        model.addAttribute("key", key);
        model.addAttribute("encodedQuery", encodedQuery);
        model.addAttribute("encodedKey", encodedKey);

        return "search/search-result-page";
    }

    @GetMapping(path = "/results-by-sse")
    public Flux<ServerSentEvent<String>> searchBySSE(@RequestParam("key") String key, @RequestParam("query") String query, Model model) {
        final String completedMessage = templateEngine.process("search/search-result-page-completed-msg", new Context()).replaceAll("\n", "");
        return Flux.create(sink -> {
            explorerService.search(QueryCommand.create(key, query))
                    .map(shellDescriptor -> {
                        Context context = new Context();
                        context.setVariable("shellDescriptor", shellDescriptor);
                        return ServerSentEvent.<String>builder()
                                .event("data")
                                .data(templateEngine.process("search/search-result-page-data-inner-row", context).replaceAll("\n", ""))
                                .build();
                    })
                    .onErrorContinue(ResourceNotFoundException.class, (throwable, o) -> {
                    })
                    .doOnComplete(() -> {
                                sink.next(ServerSentEvent.<String>builder()
                                        .event("completed")
                                        .data(completedMessage)
                                        .build());
                            }
                    )
                    .subscribe(payload -> {
                        if (payload != null) {
                            sink.next(payload);
                        }
                    });
        });
    }

    @GetMapping("/submodel")
    public String getSubmodelPage(Model model) {
        return "search/submodel-page";
    }

    @PostMapping("/submodel")
    public String getSubmodelData(Model model, @RequestParam("url") String url) {
        throw new UnsupportedOperationException();
    }
}
