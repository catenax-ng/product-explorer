package net.catenax.explorer.core.twinregistry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class Endpoint {
    @JsonProperty("interface")
    @NotNull
    @Schema(example = "SUBMODEL-1.0RC02")
    private String _interface;

    @NotNull
    @Valid
    private ProtocolInformation protocolInformation;
}
