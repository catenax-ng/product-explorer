package net.catenax.explorer.core;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.SubmodelProvider;
import net.catenax.explorer.core.submodel.twinregistry.SubmodelResponse;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;
  private final SubmodelProvider submodelProvider;

  public List<SubmodelResponse> search(final String query) {
    return edcLocationProvider.getKnownEdcLocations().stream()
        .map(edcLocation -> assetRetriever.retrieve(edcLocation.getUrl()))
        .map(AssetResponse::getEndpoints) //TODO DTR will hold a list of endpoints or one endpoint ?
        .flatMap(Collection::stream)
        .map(endpointAddress -> submodelProvider.searchSubmodels(query, endpointAddress.getProtocolInformation().getEndpointAddress()))
        .filter(Objects::nonNull)
        .toList();
  }
}