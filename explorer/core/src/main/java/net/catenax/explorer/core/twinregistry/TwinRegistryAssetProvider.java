package net.catenax.explorer.core.twinregistry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;

@RequiredArgsConstructor
public class TwinRegistryAssetProvider {

  private final TwinRegistryClient client;

  public ShellDescriptorResponse search(String query, String endpointAddress) {
    final List<String> matchedSubmodelsIds = client.lookup(query, endpointAddress);
    if (matchedSubmodelsIds.isEmpty()) {
      throw new ResourceNotFoundException(query);
    }
    return client.fetchShelDescriptor(endpointAddress, matchedSubmodelsIds);
  }
}
