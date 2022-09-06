package net.catenax.explorer.core.extension;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public class ExtensionClient {

    private static final String IDS_PATH = "/api/v1/ids/data";
    private static final String PROVIDER_URL = "providerUrl";
    private final WebClient webClient;
    private final String extensionUrl;

    public Mono<EndpointDataReference> findDataReference(String query, String providerUrl) {

        var uri = UriComponentsBuilder
                .fromUriString(extensionUrl + query)
                .queryParam(PROVIDER_URL, providerUrl + IDS_PATH)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(EndpointDataReference.class)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .onErrorResume(throwable -> Mono.empty());
    }
}
