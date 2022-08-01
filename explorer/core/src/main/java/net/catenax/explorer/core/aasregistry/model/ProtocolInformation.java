package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProtocolInformation {

    @NotNull
    @Size(min = 1, max = 512)
    private String endpointAddress;

    @Size(min = 1, max = 50)
    private String endpointProtocol;

    @Size(min = 1, max = 24)
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
