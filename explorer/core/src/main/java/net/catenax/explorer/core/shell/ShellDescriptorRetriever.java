package net.catenax.explorer.core.shell;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class ShellDescriptorRetriever {

    private final WebClient client;

    public Flux<ShellDescriptor> retrieve(Optional<EndpointDataReference> maybeEndpointDataReference) {
        return maybeEndpointDataReference.map(endpointDataReference -> {
            log.info("Retrieving shell from {}", endpointDataReference.getEndpoint());
            return client.get()
                    .uri(endpointDataReference.getEndpoint())
                    .header(resolveHeader(endpointDataReference), endpointDataReference.getAuthCode())
                    .retrieve()
                    .bodyToFlux(ShellDescriptor.class);
        }).orElseGet(Flux::empty);
    }

    private String resolveHeader(EndpointDataReference endpointDataReference) {
        return isNull(endpointDataReference.getAuthKey()) ? AUTHORIZATION : endpointDataReference.getAuthKey();
    }
}
