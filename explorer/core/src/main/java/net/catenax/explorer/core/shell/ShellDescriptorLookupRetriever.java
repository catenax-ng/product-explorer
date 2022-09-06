package net.catenax.explorer.core.shell;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.http.HttpStatus;
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
public class ShellDescriptorLookupRetriever {

    private final WebClient client;

    public Flux<String> lookupIds(Mono<EndpointDataReference> maybeEndpointDataReference, String query) {
        if(query.equals("")) {
            return Flux.empty();
        }

        return maybeEndpointDataReference.flatMapMany(endpointDataReference -> {
            log.info("Retrieving shell from {}", endpointDataReference.getEndpoint());
            try {
                final String encodedQuery = URLEncoder.encode(query, "UTF-8").replaceAll("\\+", "%20");
                return client.get()
                        .uri(endpointDataReference.getEndpoint() + "?" + encodedQuery)
                        .header(resolveHeader(endpointDataReference), endpointDataReference.getAuthCode())
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.empty())
                        .bodyToFlux(String.class)
                        .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                        .onErrorResume(throwable -> Flux.empty());
            } catch (UnsupportedEncodingException e) {
                log.error("Encoding query param error", e);
                return Flux.empty();
            }
        });
    }
    private String resolveHeader(EndpointDataReference endpointDataReference) {
        return isNull(endpointDataReference.getAuthKey()) ? AUTHORIZATION : endpointDataReference.getAuthKey();
    }
}
