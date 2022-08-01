package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * AssetAdministrationShellDescriptor
 */
@Data
public class AssetAdministrationShellDescriptor {

    @Valid
    private List<LangString> description = null;
    @Valid
    private Reference globalAssetId;

    @NotNull
    @Size(min = 1, max = 100)
    private String idShort;

    @NotNull
    @Size(min = 1, max = 200)
    private String identification;

    @Valid
    private List<IdentifierKeyValuePair> specificAssetIds = null;

    @Valid
    private List<SubmodelDescriptor> submodelDescriptors = null;

    @Valid
    private List<Endpoint> endpoints = null;


    public AssetAdministrationShellDescriptor addDescriptionItem(LangString descriptionItem) {
        if (this.description == null) {
            this.description = new ArrayList<>();
        }
        this.description.add(descriptionItem);
        return this;
    }

    public AssetAdministrationShellDescriptor addSpecificAssetIdsItem(IdentifierKeyValuePair specificAssetIdsItem) {
        if (this.specificAssetIds == null) {
            this.specificAssetIds = new ArrayList<>();
        }
        this.specificAssetIds.add(specificAssetIdsItem);
        return this;
    }

    public AssetAdministrationShellDescriptor addSubmodelDescriptorsItem(SubmodelDescriptor submodelDescriptorsItem) {
        if (this.submodelDescriptors == null) {
            this.submodelDescriptors = new ArrayList<>();
        }
        this.submodelDescriptors.add(submodelDescriptorsItem);
        return this;
    }

    public AssetAdministrationShellDescriptor addEndpointsItem(Endpoint endpointsItem) {
        if (this.endpoints == null) {
            this.endpoints = new ArrayList<>();
        }
        this.endpoints.add(endpointsItem);
        return this;
    }
}

