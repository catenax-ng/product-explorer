package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;

  public AssetData fetchAssetById(final String id) {

    final AssetResponse asset =
        edcLocationProvider.getKnownEdcLocations().stream()
            .map(edcLocation -> assetRetriever.retrieve(edcLocation.getUrl()))
            .filter(assetResponse -> assetResponse.getIdentification().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(id));
    return AssetData.from(asset);
  }
}