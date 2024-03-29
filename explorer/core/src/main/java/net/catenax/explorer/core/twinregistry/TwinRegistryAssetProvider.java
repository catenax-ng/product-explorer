package net.catenax.explorer.core.twinregistry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.shell.ShellDescriptorResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class TwinRegistryAssetProvider {

  private final TwinRegistryClient client;

  public Flux<ShellDescriptorResponse> search(String query, String endpointAddress) {
    final Mono<List<String>> matchedSubmodelsIds = client.lookup(query, endpointAddress);
    return matchedSubmodelsIds
        .flux()
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .flatMap(n -> client.fetchShelDescriptor(endpointAddress, matchedSubmodelsIds))
        .sequential()
        .doOnNext(n -> log.info(n.toString()));
  }
}