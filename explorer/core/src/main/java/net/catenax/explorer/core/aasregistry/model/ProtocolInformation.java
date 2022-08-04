package net.catenax.explorer.core.aasregistry.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
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

    public ProtocolInformation endpointAddress(String endpointAddress) {
        this.endpointAddress = endpointAddress;
        return this;
    }


    public ProtocolInformation endpointProtocol(String endpointProtocol) {
        this.endpointProtocol = endpointProtocol;
        return this;
    }

    public ProtocolInformation endpointProtocolVersion(String endpointProtocolVersion) {
        this.endpointProtocolVersion = endpointProtocolVersion;
        return this;
    }


    public ProtocolInformation subprotocol(String subprotocol) {
        this.subprotocol = subprotocol;
        return this;
    }

    public ProtocolInformation subprotocolBody(String subprotocolBody) {
        this.subprotocolBody = subprotocolBody;
        return this;
    }

    public ProtocolInformation subprotocolBodyEncoding(String subprotocolBodyEncoding) {
        this.subprotocolBodyEncoding = subprotocolBodyEncoding;
        return this;
    }
}
