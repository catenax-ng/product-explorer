package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Metadata {
    @NotNull
    @Size(min = 1, max = 512)
    private String digitalTwinRegistryUrl;

    @NotNull
    @Size(min = 1, max = 50)
    private String bpn;

    @NotNull
    @Size(min = 1, max = 512)
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
