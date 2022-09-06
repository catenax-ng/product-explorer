package net.catenax.explorer.core.extension;

import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import reactor.core.publisher.Mono;

public interface DataReferenceProvider {

  Mono<EndpointDataReference> search(String query, String providerControlPlaneUrl);
}
