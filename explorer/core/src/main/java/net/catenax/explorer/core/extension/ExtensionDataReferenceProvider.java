package net.catenax.explorer.core.extension;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ExtensionDataReferenceProvider implements DataReferenceProvider {

  private final ExtensionClient client;

  @Override
  public Mono<EndpointDataReference> search(String query, String providerControlPlaneUrl) {
    return client.findDataReference(query, providerControlPlaneUrl);
  }
}
