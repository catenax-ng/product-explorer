package net.catenax.explorer.core.extension;

import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

import java.util.Optional;

public interface DataReferenceProvider {

  Optional<EndpointDataReference> search(String query, String providerControlPlaneUrl);
}
