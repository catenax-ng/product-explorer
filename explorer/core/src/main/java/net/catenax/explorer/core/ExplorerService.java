package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.extension.DataReferenceProvider;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import net.catenax.explorer.core.shell.ShellDescriptorRetriever;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final DataReferenceProvider dataReferenceProvider;
  private final ShellDescriptorRetriever shellDescriptorRetriever;

  public Flux<ShellDescriptor> search(final String query) {
    return Flux.fromIterable(edcLocationProvider.getKnownEdcLocations())
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .map(location -> dataReferenceProvider.search(query, location.getServiceProvider()))
        .flatMap(shellDescriptorRetriever::retrieve)
        .sequential()
        .doOnNext(shellDescriptor -> log.info("Got shell descriptor: " + shellDescriptor));
  }
}
