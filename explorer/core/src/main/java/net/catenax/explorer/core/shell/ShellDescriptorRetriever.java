package net.catenax.explorer.core.shell;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class ShellDescriptorRetriever {

    private final WebClient client;

    public Flux<String> lookupIds(Mono<EndpointDataReference> potentialEndpointDataReference, String query) {
        if (query.isBlank()) {
            return Flux.empty();
        }

        return potentialEndpointDataReference.flatMapMany(endpointDataReference -> {
            try {
                final String url = endpointDataReference.getEndpoint() + "?assetIds=" + URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20");
                log.info("Retrieving shell from {}", url);
                return retrieve(endpointDataReference, url, String.class);
            } catch (UnsupportedEncodingException e) {
                log.error("Encoding query param error", e);
                return Flux.empty();
            }
        });
    }

    public Flux<ShellDescriptor> retrieve(Mono<EndpointDataReference> potentialEndpointDataReference) {
        return potentialEndpointDataReference.flatMapMany(endpointDataReference ->
                retrieve(endpointDataReference, endpointDataReference.getEndpoint(), ShellDescriptor.class)
        );
    }

    private <T> Flux<T> retrieve(EndpointDataReference endpointDataReference, String url, Class<T> clazz) {
        log.info("Retrieving shell from {}", url);
        return client.get()
                .uri(url)
                .header(resolveHeader(endpointDataReference), endpointDataReference.getAuthCode())
                .retrieve()
                .bodyToFlux(clazz)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .onErrorResume(throwable -> Flux.empty());
    }

    private String resolveHeader(EndpointDataReference endpointDataReference) {
        return isNull(endpointDataReference.getAuthKey()) ? AUTHORIZATION : endpointDataReference.getAuthKey();
    }
}
