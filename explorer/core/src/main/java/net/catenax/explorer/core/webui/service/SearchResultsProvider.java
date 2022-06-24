package net.catenax.explorer.core.webui.service;

import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

import java.util.List;

public interface SearchResultsProvider {
    List<ShellDescriptorResponse.ShellDescriptor> search(String query);
}
