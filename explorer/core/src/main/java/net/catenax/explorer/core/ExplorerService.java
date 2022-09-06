package net.catenax.explorer.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.extension.DataReferenceProvider;
import net.catenax.explorer.core.shell.ShellDescriptorLookupRetriever;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import net.catenax.explorer.core.shell.ShellDescriptorRetriever;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ExplorerService {

  private final EdcLocationProvider edcLocationProvider;
  private final DataReferenceProvider dataReferenceProvider;
  private final ShellDescriptorRetriever shellDescriptorRetriever;
  private final ShellDescriptorLookupRetriever shellDescriptorLookupRetriever;

  private final ObjectMapper objectMapper;

  public Flux<ShellDescriptor> search(final String query) {
    if(query.startsWith("urn:uuid:")) {
      return searchById(query);
    }

    return searchAssetIdByQuery(query)
            .distinct()
            .flatMap(this::searchById);
  }

  private Flux<ShellDescriptor> searchById(final String assetId) {
    return edcLocationProvider.getKnownEdcLocationsStream()
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .map(location -> dataReferenceProvider.search(assetId, location.getServiceProvider()))
        .flatMap(shellDescriptorRetriever::retrieve)
        .sequential()
        .doOnNext(shellDescriptor -> log.info("Got shell descriptor: " + shellDescriptor));
  }

  private Flux<String> searchAssetIdByQuery(final String query) {

    return edcLocationProvider.getKnownEdcLocationsStream()
            .parallel()
            .runOn(Schedulers.boundedElastic())
            .map(location -> dataReferenceProvider.search("search", location.getServiceProvider())) // TODO: configurable search param
            .flatMap(endpointDataReference -> shellDescriptorLookupRetriever.lookupIds(endpointDataReference, buildLookupQuery(query)))
            .sequential()
            .flatMap(jsonAssetResponse -> {
              try {
                return Flux.fromArray(objectMapper.readValue(jsonAssetResponse, String[].class));
              } catch (JsonProcessingException e) {
                log.error("Cannot parse lookup response", e);
                return Flux.empty();
              }
            })
            .doOnNext(assetId -> log.info("Got assetId: " + assetId));
  }

  private String buildLookupQuery(String query) {
    try {
      if (query.startsWith("assetIds=[")) {
        return query;
      }
      final String filterBody = Arrays.stream(query.split(","))
              .distinct()
              .map(filter -> {
                final String[] values = filter.split(":");
                return "\"key\": \"%s\", \"value\": \"%s\"" .formatted(values[0].trim(), values[1].trim());
              })
              .collect(Collectors.joining("},{"));
      return "assetIds=[{%s}]" .formatted(filterBody);
    } catch (Exception e) {
       return "";
    }
  }
}
