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
  public AssetResponse retrieve(final String edcEndpoint) {
    log.info("Retrieving asset from {}", edcEndpoint);
    return buildAssetResponseMock();
  }

  private AssetResponse buildAssetResponseMock() {
    return AssetResponse.builder()
        .identification("urn:uuid:2229e7c4-940a-498f-8501-6bd216e09eb7")
        .idShort("serialPartTypization")
        .semanticId(buildSemanticId())
        .endpoints(List.of(buildEndpoint()))
        .validUntil(now().plus(ofMinutes(10)))
        .build();
  }

  private Endpoint buildEndpoint() {
    return Endpoint.builder()
        .interfaceType("EDC")
        .protocolInformation(
            buildProtocolInformation())
        .build();
  }

  private ProtocolInformation buildProtocolInformation() {
    return ProtocolInformation.builder()
        .endpointAddress("http://localhost:4243")
        .endpointProtocol("IDS/ECLIPSE DATASPACE CONNECTOR")
        .endpointProtocolVersion("0.0.1-SNAPSHOT")
        .build();
  }

  private SemanticId buildSemanticId() {
    return SemanticId.builder()
        .value(List.of("urn:example.catenax.serial_part_typization:1.0.0"))
        .build();
  }
}