package net.catenax.explorer.core.twinregistry;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.edclocation.model.SelfDescription;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;
  private final TwinRegistryAssetProvider twinRegistryAssetProvider;

  public List<SearchShellDescriptorResults> searchTwinRegistries(final String query, final String bpn) {

    return edcLocationProvider.getKnownEdcLocations().stream()
        .filter(edcLocation -> isNull(bpn) || bpn.equals(edcLocation.getBpn()))
        .map(edcLocation -> {
          final AssetResponse dtr = assetRetriever.retrieve(edcLocation.getServiceProvider());
          final List<ShellDescriptor> shellDescriptors = searchShells(query, dtr);
          return buildSearchResult(edcLocation, dtr, shellDescriptors);
        })
        .toList();
  }

  private SearchShellDescriptorResults buildSearchResult(SelfDescription edcLocation, AssetResponse dtr, List<ShellDescriptor> shellDescriptors) {
    return SearchShellDescriptorResults.builder()
        .metadata(ShellDescriptorMetaData.builder()
            .digitalTwinRegistryUrl(dtr.getEndpoints().get(0).getProtocolInformation()
                .getEndpointAddress()) //TODO See if assetRetriever can return a list of twin registry address, if yes, then the API need to be modified
            .bpn(edcLocation.getBpn())
            .edcUrl(edcLocation.getServiceProvider())
            .build())
        .items(shellDescriptors)
        .build();
  }

  private List<ShellDescriptor> searchShells(String query, AssetResponse dtr) {
    return dtr.getEndpoints().stream()
        .map(endpointAddress -> {
          final Flux<ShellDescriptorResponse> search = twinRegistryAssetProvider.search(query, endpointAddress.getProtocolInformation().getEndpointAddress());
          return search.blockLast();
        })
        .map(ShellDescriptorResponse::getItems)
        .flatMap(Collection::stream)
        .toList();
  }

  public record SearchShellDescriptorResults(ShellDescriptorMetaData metadata, List<ShellDescriptor> items) {

    @Builder
    public SearchShellDescriptorResults {
    }
  }

  record ShellDescriptorMetaData(String digitalTwinRegistryUrl, String bpn, String edcUrl) {

    @Builder
    ShellDescriptorMetaData {
    }
  }
}