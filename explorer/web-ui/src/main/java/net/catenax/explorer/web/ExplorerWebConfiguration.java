package net.catenax.explorer.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ExplorerWebConfiguration {

    @Value("${explorer-application-url:http://localhost:8080}")
    private String explorerApplicationUrl;

    @Bean
    ExplorerWebController explorerWebController() {
        return new ExplorerWebController(explorerApplicationUrl);
    }

}
