package net.catenax.explorer.core.submodel.twinregistry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.submodel.SubmodelProvider;

@RequiredArgsConstructor
public class TwinRegistryAssetProvider implements SubmodelProvider {

  private final TwinRegistryClient client;

  @Override
  public SubmodelResponse searchSubmodels(String query, String endpointAddress) {
    final List<String> matchedSubmodelsIds = client.lookup(query, endpointAddress);
    return client.fetchSubmodels(endpointAddress, matchedSubmodelsIds);
  }
}