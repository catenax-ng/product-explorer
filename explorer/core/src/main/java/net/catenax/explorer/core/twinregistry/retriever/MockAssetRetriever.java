package net.catenax.explorer.core.twinregistry.retriever;

import static java.time.Duration.ofMinutes;
import static java.time.Instant.now;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.twinregistry.retriever.AssetResponse.Endpoint;
import net.catenax.explorer.core.twinregistry.retriever.AssetResponse.Endpoint.ProtocolInformation;
import net.catenax.explorer.core.twinregistry.retriever.AssetResponse.SemanticId;

@Slf4j
public class MockAssetRetriever implements AssetRetriever {

  @Override
  public AssetResponse retrieve(final String edcEndpoint) {
    log.info("Retrieving asset from {}", edcEndpoint);
    return buildAssetResponseMock(edcEndpoint);
  }

  private AssetResponse buildAssetResponseMock(final String edcEndpoint) {
    return AssetResponse.builder()
        .identification("urn:uuid:2229e7c4-940a-498f-8501-6bd216e09eb7")
        .idShort("serialPartTypization")
        .semanticId(buildSemanticId())
        .endpoints(List.of(buildEndpoint(edcEndpoint)))
        .validUntil(now().plus(ofMinutes(10)))
        .build();
  }

  private Endpoint buildEndpoint(final String edcEndpoint) {
    return Endpoint.builder()
        .interfaceType("EDC")
        .protocolInformation(
            buildProtocolInformation(edcEndpoint))
        .build();
  }

  private ProtocolInformation buildProtocolInformation(final String edcEndpoint) {
    return ProtocolInformation.builder()
        .endpointAddress(resolveTwinRegistryAddress(edcEndpoint))
        .endpointProtocol("IDS/ECLIPSE DATASPACE CONNECTOR")
        .endpointProtocolVersion("0.0.1-SNAPSHOT")
        .build();
  }

  private SemanticId buildSemanticId() {
    return SemanticId.builder()
        .value(List.of("urn:example.catenax.serial_part_typization:1.0.0"))
        .build();
  }

  private String resolveTwinRegistryAddress(String endEndpoint) {
    return switch (endEndpoint) {
      case "http://provider-controlplane-bmw:8282" ->  "localhost:4244";
      case "http://provider-controlplane-daimler:8282" ->  "localhost:4246";
      default -> "localhost:4244";
    };
  }
}