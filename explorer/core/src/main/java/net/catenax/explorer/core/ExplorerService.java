package net.catenax.explorer.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.extension.DataReferenceProvider;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import net.catenax.explorer.core.shell.ShellDescriptorRetriever;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class ExplorerService {

    private final EdcLocationProvider edcLocationProvider;
    private final DataReferenceProvider dataReferenceProvider;
    private final ShellDescriptorRetriever shellDescriptorRetriever;
    private final ObjectMapper objectMapper;

    private final String searchAssetName;


    public Flux<ShellDescriptor> search(final QueryCommand queryCommand) {
        return queryCommand.getIdValue()
                .map(this::searchById)
                .orElseGet(() -> searchByParameters(queryCommand));
    }

    @NotNull
    private Flux<ShellDescriptor> searchByParameters(QueryCommand queryCommand) {
        return searchAssetIdByQuery(queryCommand)
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

    private Flux<String> searchAssetIdByQuery(final QueryCommand queryCommand) {
        return edcLocationProvider.getKnownEdcLocationsStream()
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .map(location -> dataReferenceProvider.search(searchAssetName, location.getServiceProvider()))
                .flatMap(endpointDataReference -> shellDescriptorRetriever.lookupIds(endpointDataReference, queryCommand))
                .sequential()
                .flatMap(parseToSeparateValues())
                .doOnNext(assetId -> log.info("Got assetId: " + assetId));
    }

    @NotNull
    private Function<String, Publisher<? extends String>> parseToSeparateValues() {
        return jsonAssetResponse -> {
            try {
                return Flux.fromArray(objectMapper.readValue(jsonAssetResponse, String[].class));
            } catch (JsonProcessingException e) {
                log.error("Cannot parse lookup response", e);
                return Flux.empty();
            }
        };
    }

}
