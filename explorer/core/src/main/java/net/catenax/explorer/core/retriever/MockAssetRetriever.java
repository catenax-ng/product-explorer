package net.catenax.explorer.core.retriever;

import static java.time.Duration.ofMinutes;
import static java.time.Instant.now;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.retriever.AssetResponse.Endpoint;
import net.catenax.explorer.core.retriever.AssetResponse.Endpoint.ProtocolInformation;
import net.catenax.explorer.core.retriever.AssetResponse.SemanticId;

@Slf4j
public class MockAssetRetriever implements AssetRetriever {

  @Override
  //rename to something with DTR
  public AssetResponse retrieve(final String edcEndpoint) {
    log.info("Retrieving asset from {}", edcEndpoint);
    return AssetResponse.builder()
        .identification("urn:uuid:2229e7c4-940a-498f-8501-6bd216e09eb7")
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
                            .endpointAddress("http://localhost:4243")
                            .endpointProtocol("IDS/ECLIPSE DATASPACE CONNECTOR")
                            .endpointProtocolVersion("0.0.1-SNAPSHOT")
                            .build())
                    .build()))
        .validUntil(now().plus(ofMinutes(10)))
        .build();
  }
}
