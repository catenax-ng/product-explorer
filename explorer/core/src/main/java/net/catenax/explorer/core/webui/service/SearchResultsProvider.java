package net.catenax.explorer.core.webui.service;

import net.catenax.explorer.core.submodel.ShellDescriptorResponse;

import java.util.List;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;

public interface SearchResultsProvider {
    Flux<ShellDescriptor> search(String query);
}
