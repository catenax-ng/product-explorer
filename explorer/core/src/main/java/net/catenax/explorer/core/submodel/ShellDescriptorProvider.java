package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse.ShellDescriptor;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

public interface ShellDescriptorProvider {

  ShellDescriptor search(String query, String endPointAddress);

  void persistCallback(EndpointDataReference endpointDataReference);
}