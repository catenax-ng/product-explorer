package net.catenax.explorer.core.aasregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
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

    public Metadata digitalTwinRegistryUrl(String digitalTwinRegistryUrl) {
        this.digitalTwinRegistryUrl = digitalTwinRegistryUrl;
        return this;
    }


    public Metadata bpn(String bpn) {
        this.bpn = bpn;
        return this;
    }

    public Metadata edcUrl(String edcUrl) {
        this.edcUrl = edcUrl;
        return this;
    }
}
