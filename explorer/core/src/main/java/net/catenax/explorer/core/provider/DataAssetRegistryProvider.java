package net.catenax.explorer.core.provider;

import java.util.List;

public interface DataAssetRegistryProvider {

  //TODO see how many DARs could be retrived
  List<DataAssetRegistryResponse> retrieveDars(String edcEndpoint);
}