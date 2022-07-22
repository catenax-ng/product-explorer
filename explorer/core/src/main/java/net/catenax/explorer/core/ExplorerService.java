package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final ShellDescriptorProvider shellDescriptorProvider;

  public Flux<ShellDescriptor> search(final String query) {
    return Flux.fromIterable(edcLocationProvider.getKnownEdcLocations())
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .flatMap(selfDescription -> shellDescriptorProvider.search(query, selfDescription.getServiceProvider()))
        .sequential()
        .doOnNext(shellDescriptor -> log.info("Got shell descriptor: " + shellDescriptor));
  }
}