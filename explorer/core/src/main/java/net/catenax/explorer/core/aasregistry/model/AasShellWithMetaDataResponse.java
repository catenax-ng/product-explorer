package net.catenax.explorer.core.aasregistry.model;

import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.Valid;
import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class AasShellWithMetaDataResponse {
    @Valid
    private Metadata metadata;

    @Valid
    private List<AssetAdministrationShellDescriptor> items = null;
}
