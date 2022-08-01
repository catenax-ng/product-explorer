package net.catenax.explorer.core.aasregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Endpoint {
    @JsonProperty("interface")
    @NotNull
    private String _interface;

    @NotNull
    @Valid
    private ProtocolInformation protocolInformation;

    public Endpoint _interface(String _interface) {
        this._interface = _interface;
        return this;
    }

    public Endpoint protocolInformation(ProtocolInformation protocolInformation) {
        this.protocolInformation = protocolInformation;
        return this;
    }
}
