package net.catenax.explorer.core.extension;

import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

public interface DataReferenceProvider {

  EndpointDataReference search(String query, String providerControlPlaneUrl);
}
