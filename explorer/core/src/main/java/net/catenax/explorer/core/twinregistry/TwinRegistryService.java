package net.catenax.explorer.core.twinregistry;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryService {

  private final EdcLocationProvider edcLocationProvider;
  private final AssetRetriever assetRetriever;
  private final TwinRegistryAssetProvider twinRegistryAssetProvider; //TODO see if could be ShellDescriptorprovider interface using flux

  public List<SearchShellDescriptorResults> searchTwinRegistries(final String query, final String bpn) {

    return edcLocationProvider.getKnownEdcLocations().stream()
        .filter(edcLocation -> isNull(bpn) || bpn.equals(edcLocation.getBpn()))
        .map(edcLocation -> {
          final AssetResponse dtr = assetRetriever.retrieve(edcLocation.getServiceProvider());

          final List<ShellDescriptor> shellDescriptors = dtr.getEndpoints().stream()
              .map(endpointAddress -> twinRegistryAssetProvider.search(query, endpointAddress.getProtocolInformation().getEndpointAddress()))
              .filter(Objects::nonNull)
              .map(ShellDescriptorResponse::getItems)
              .flatMap(Collection::stream)
              .toList();

          return SearchShellDescriptorResults.builder()
              .metadata(ShellDescriptorMetaData.builder()
                  .aasRegistryUrl(dtr.getEndpoints().get(0).getProtocolInformation()
                      .getEndpointAddress()) //TODO See if assetRetriever can return a list of twin registry address, if yes, then the API need to be modified
                  .bpn(edcLocation.getBpn())
                  .edcUrl(edcLocation.getServiceProvider())
                  .build())
              .items(shellDescriptors)
              .build();

        })
        .toList();
  }

  record SearchShellDescriptorResults(ShellDescriptorMetaData metadata, List<ShellDescriptor> items) {

    @Builder
    SearchShellDescriptorResults {
    }
  }

  record ShellDescriptorMetaData(String aasRegistryUrl, String bpn, String edcUrl) {

    @Builder
    ShellDescriptorMetaData {
    }
  }
}