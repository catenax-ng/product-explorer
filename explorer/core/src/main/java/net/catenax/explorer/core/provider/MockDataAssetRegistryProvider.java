package net.catenax.explorer.core.provider;

import static java.time.Duration.ofMinutes;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.provider.DataAssetRegistryResponse.Endpoint;
import net.catenax.explorer.core.provider.DataAssetRegistryResponse.Endpoint.ProtocolInformation;
import net.catenax.explorer.core.provider.DataAssetRegistryResponse.SemanticId;

@Slf4j
public class MockDataAssetRegistryProvider implements DataAssetRegistryProvider {

  @Override
  public List<DataAssetRegistryResponse> retrieveDars(final String edcEndpoint) {
    log.info("Retrieving DAR from {}", edcEndpoint);
    return List.of(
        DataAssetRegistryResponse.builder()
            .identification("urn:uuid:" + randomUUID())
            .idShort("serialPartTypization")
            .semanticId(SemanticId.builder()
                .value(List.of("urn:example.catenax.serial_part_typization:1.0.0"))
                .build()
            )
            .endpoints(List.of(
                Endpoint.builder()
                    .interfaceType("EDC")
                    .protocolInformation(
                        ProtocolInformation.builder()
                            .endpointAddress("http://connector.example:8080/id")
                            .endpointProtocol("IDS/ECLIPSE DATASPACE CONNECTOR")
                            .endpointProtocolVersion("0.0.1-SNAPSHOT")
                            .build()
                    )
                    .build()
            ))
            .validUntil(now().plus(ofMinutes(10)))
            .build()
    );
  }
}