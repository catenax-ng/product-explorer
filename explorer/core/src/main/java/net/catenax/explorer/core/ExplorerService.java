package net.catenax.explorer.core;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;
  private final ShellDescriptorProvider shellDescriptorProvider;

  public List<ShellDescriptorResponse> search(final String query) {
    return edcLocationProvider.getKnownEdcLocations().stream()
        .map(selfDescription -> assetRetriever.retrieve(selfDescription.getServiceProvider()))
        .map(AssetResponse::getEndpoints) //TODO DTR will hold a list of endpoints or one endpoint ?
        .flatMap(Collection::stream)
        .map(endpointAddress -> shellDescriptorProvider.search(query, endpointAddress.getProtocolInformation().getEndpointAddress()))
        .filter(Objects::nonNull)
        .toList();
  }
}