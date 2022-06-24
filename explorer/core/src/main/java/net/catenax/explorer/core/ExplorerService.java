package net.catenax.explorer.core;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final ShellDescriptorProvider shellDescriptorProvider;

  public List<ShellDescriptorResponse> search(final String query) {
    return edcLocationProvider.getKnownEdcLocations().stream()
        .map(selfDescription -> shellDescriptorProvider.search(query, selfDescription.getServiceProvider()))
        .filter(Objects::nonNull)
        .toList();
  }
}