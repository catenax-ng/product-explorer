package net.catenax.explorer.core.submodel;

import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

public interface ShellDescriptorProvider {

  String search(String query, String endPointAddress);

  void persistCallback(EndpointDataReference endpointDataReference);
}