package net.catenax.explorer.core;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;

@RequiredArgsConstructor
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final ShellDescriptorProvider shellDescriptorProvider;

  public List<String> search(final String query) {
    return edcLocationProvider.getKnownEdcLocations().stream()
        .map(selfDescription -> shellDescriptorProvider.search(query, selfDescription.getServiceProvider()))
        .filter(Objects::nonNull)
        .toList();
  }
}