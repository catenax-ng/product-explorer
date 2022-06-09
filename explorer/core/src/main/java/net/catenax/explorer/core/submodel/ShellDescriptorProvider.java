package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;

public interface ShellDescriptorProvider {

    ShellDescriptorResponse search(String query, String endPointAddress);

}
