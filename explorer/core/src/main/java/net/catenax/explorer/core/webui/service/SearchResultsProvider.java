package net.catenax.explorer.core.webui.service;

import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import reactor.core.publisher.Flux;

public interface SearchResultsProvider {
    Flux<ShellDescriptor> search(String query);

    String getSubmodelData(String url);
}
