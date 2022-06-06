package net.catenax.explorer.core.submodel;

import net.catenax.explorer.core.submodel.twinregistry.SubmodelResponse;

public interface SubmodelProvider {

    SubmodelResponse searchSubmodels(String query, String endPointAddress);

}
