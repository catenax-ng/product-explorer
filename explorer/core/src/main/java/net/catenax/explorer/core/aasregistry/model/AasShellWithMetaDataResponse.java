package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class AasShellWithMetaDataResponse {
    @Valid
    private Metadata metadata;

    @Valid
    private List<AssetAdministrationShellDescriptor> items = null;

    public AasShellWithMetaDataResponse metadata(Metadata metadata) {
        this.metadata = metadata;
        return this;
    }


    public AasShellWithMetaDataResponse items(List<AssetAdministrationShellDescriptor> items) {
        this.items = items;
        return this;
    }

    public AasShellWithMetaDataResponse addItemsItem(AssetAdministrationShellDescriptor itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(itemsItem);
        return this;
    }
}
