package net.catenax.explorer.core;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.SubmodelProvider;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;
  private final SubmodelProvider submodelProvider;

  public AssetData search(final String query) {

    edcLocationProvider.getKnownEdcLocations().stream()
        .map(edcLocation -> assetRetriever.retrieve(edcLocation.getUrl()))
        .map(AssetResponse::getEndpoints) //TODO DTR will hol a list of endpoints or one endpoint ?
        .flatMap(Collection::stream)
        .forEach(endpointAddress -> submodelProvider.searchSubModels(query, endpointAddress.getProtocolInformation().getEndpointAddress()));
    return null;
  }
}