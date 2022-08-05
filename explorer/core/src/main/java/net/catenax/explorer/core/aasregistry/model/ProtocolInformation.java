package net.catenax.explorer.core.aasregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@NoArgsConstructor(force = true)
public class ProtocolInformation {

    @NotNull
    @Size(min = 1, max = 512)
    @Schema(example = "edc://provider.connector:9191/offer-windchill/shells/882fc530-b69b-4707-95f6-5dbc5e9baaa8/aas/4a738a24-b7d8-4989-9cd6-387772f40565/submodel")
    private String endpointAddress;

    @Size(min = 1, max = 50)
    @Schema(example = "IDS/ECLIPSE DATASPACE CONNECTOR")
    private String endpointProtocol;

    @Size(min = 1, max = 24)
    @Schema(example = "0.0.1")
    private String endpointProtocolVersion;

    @Size(min = 1, max = 50)
    private String subprotocol;

    @Size(min = 1, max = 50)
    private String subprotocolBody;

    @Size(min = 1, max = 50)
    private String subprotocolBodyEncoding;
}
