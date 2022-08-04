package net.catenax.explorer.core.aasregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Endpoint {
    @JsonProperty("interface")
    @NotNull
    @Schema(example = "SUBMODEL-1.0RC02")
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
