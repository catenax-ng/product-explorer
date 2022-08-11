package net.catenax.explorer.core.twinregistry.model;

import java.util.List;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class AasShellWithMetaDataResponse {
    @Valid
    private Metadata metadata;

    @Valid
    private List<AssetAdministrationShellDescriptor> items = null;
}
