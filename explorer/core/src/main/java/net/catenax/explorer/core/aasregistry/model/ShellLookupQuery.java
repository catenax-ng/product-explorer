package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShellLookupQuery {
    @Valid
    private List<IdentifierKeyValuePair> assetIds = null;

    public ShellLookupQuery assetIds(List<IdentifierKeyValuePair> assetIds) {
        this.assetIds = assetIds;
        return this;
    }

    public ShellLookupQuery addAssetIdsItem(IdentifierKeyValuePair assetIdsItem) {
        if (this.assetIds == null) {
            this.assetIds = new ArrayList<>();
        }
        this.assetIds.add(assetIdsItem);
        return this;
    }
}
