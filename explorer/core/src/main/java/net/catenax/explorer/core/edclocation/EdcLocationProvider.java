package net.catenax.explorer.core.edclocation;

import net.catenax.explorer.core.edclocation.model.SelfDescription;
import reactor.core.publisher.Flux;

import java.util.List;

public interface EdcLocationProvider {
    List<SelfDescription> getKnownEdcLocations();
    Flux<SelfDescription> getKnownEdcLocationsStream();
}
