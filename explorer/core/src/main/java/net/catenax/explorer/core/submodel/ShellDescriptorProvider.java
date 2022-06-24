package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

public interface ShellDescriptorProvider {

  ShellDescriptorResponse search(String query, String endPointAddress);

  void persistCallback(EndpointDataReference endpointDataReference);
}