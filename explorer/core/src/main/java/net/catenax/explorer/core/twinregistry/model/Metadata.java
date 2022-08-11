package net.catenax.explorer.core.twinregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class Metadata {
    @NotNull
    @Size(min = 1, max = 512)
    @Schema(example = "https://example-aas-registry.com/")
    private String digitalTwinRegistryUrl;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(example = "BPN102384028F")
    private String bpn;

    @NotNull
    @Size(min = 1, max = 512)
    @Schema(example = "https://example-edc-url.com/")
    private String edcUrl;
}
