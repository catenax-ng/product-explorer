package net.catenax.explorer.core.submodel.twinregistry;

import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.submodel.SubmodelProvider;

@RequiredArgsConstructor
public class TwinRegistryAssetProvider implements SubmodelProvider {

  private final TwinRegistryClient client;

  @Override
  public void searchSubmodels(String query, String endpointAddress) {
    client.lookup(query, endpointAddress);
  }
}
