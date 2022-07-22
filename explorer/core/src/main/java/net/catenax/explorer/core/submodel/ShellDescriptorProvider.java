package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import reactor.core.publisher.Flux;

public interface ShellDescriptorProvider {

  Flux<ShellDescriptor> search(String query, String endPointAddress);

  void persistCallback(EndpointDataReference endpointDataReference);
}