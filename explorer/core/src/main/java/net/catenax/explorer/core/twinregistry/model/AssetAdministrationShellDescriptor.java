package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * AssetAdministrationShellDescriptor
 */
@Value
@NoArgsConstructor(force = true)
public class AssetAdministrationShellDescriptor {

    @Valid
    private List<LangString> description = null;
    @Valid
    private Reference globalAssetId;

    @NotNull
    @Size(min = 1, max = 100)
    @Schema(example = "future concept x")
    private String idShort;

    @NotNull
    @Size(min = 1, max = 200)
    @Schema(example = "882fc530-b69b-4707-95f6-5dbc5e9baaa8")
    private String identification;

    @Valid
    private List<IdentifierKeyValuePair> specificAssetIds = null;

    @Valid
    private List<SubmodelDescriptor> submodelDescriptors = null;

    @Valid
    private List<Endpoint> endpoints = null;
}
