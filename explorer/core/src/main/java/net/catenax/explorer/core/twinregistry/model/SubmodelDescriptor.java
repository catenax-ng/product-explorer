package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class SubmodelDescriptor {
    @Valid
    private List<LangString> description = null;

    @Size(min = 1, max = 100)
    @Schema(example = "traceability-info")
    private String idShort;

    @NotNull
    @Size(min = 1, max = 200)
    @Schema(example = "4a738a24-b7d8-4989-9cd6-387772f40565")
    private String identification;

    @NotNull
    @Valid
    private Reference semanticId;

    @Valid
    private List<Endpoint> endpoints = new ArrayList<>();
}
