package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class IdentifierKeyValuePair {
    @NotNull
    @Size(min = 1, max = 200)
    @Schema(example = "PartInstanceID")
    private String key;

    @NotNull
    @Size(min = 1, max = 200)
    @Schema(example = "24975539203421")
    private String value;
}
