package net.catenax.explorer.core;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final ShellDescriptorProvider shellDescriptorProvider;

  public ShellDescriptorResponse search(final String query) {
    ShellDescriptorResponse response = new ShellDescriptorResponse();
    response.setItems(edcLocationProvider.getKnownEdcLocations().stream()
        .map(selfDescription -> shellDescriptorProvider.search(query, selfDescription.getServiceProvider()))
        .filter(Objects::nonNull)
        .toList());

    return response;
  }
}