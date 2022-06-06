package net.catenax.explorer.core.retriever;

import static java.time.Duration.ofMinutes;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import java.util.List;
import net.catenax.explorer.core.retriever.AssetResponse.Endpoint;
import net.catenax.explorer.core.retriever.AssetResponse.Endpoint.ProtocolInformation;
import net.catenax.explorer.core.retriever.AssetResponse.SemanticId;

public class AssetDataMother {

  public static AssetResponse getAssetResponse() {
    return AssetResponse.builder()
        .identification("urn:uuid:" + randomUUID())
        .idShort("serialPartTypization")
        .semanticId(
            SemanticId.builder()
                .value(List.of("urn:example.catenax.serial_part_typization:1.0.0"))
                .build())
        .endpoints(
            List.of(
                Endpoint.builder()
                    .interfaceType("EDC")
                    .protocolInformation(
                        ProtocolInformation.builder()
                            .endpointAddress("http://connector.example:8080/id")
                            .endpointProtocol("IDS/ECLIPSE DATASPACE CONNECTOR")
                            .endpointProtocolVersion("0.0.1-SNAPSHOT")
                            .build())
                    .build()))
        .validUntil(now().plus(ofMinutes(10)))
        .build();
  }
}