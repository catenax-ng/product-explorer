package net.catenax.explorer.core;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Value;
import net.catenax.explorer.core.AssetData.Endpoint.ProtocolInformation;
import net.catenax.explorer.core.retriever.AssetResponse;

@Value
@Builder
public class AssetData {

  String identification;
  String idShort;
  SemanticId semanticId;
  List<Endpoint> endpoints;
  Instant validUntil;

  @Value
  @Builder
  public static class SemanticId {

    List<String> value;
  }

  @Value
  @Builder
  public static class Endpoint {

    String interfaceType;
    ProtocolInformation protocolInformation;

    @Value
    @Builder
    public static class ProtocolInformation {

      String endpointAddress;
      String endpointProtocol;
      String endpointProtocolVersion;
    }
  }

  public static AssetData from(AssetResponse asset) {
    return AssetData.builder()
        .idShort(asset.getIdShort())
        .identification(asset.getIdentification())
        .validUntil(asset.getValidUntil())
        .semanticId(SemanticId.builder().value(asset.getSemanticId().getValue()).build())
        .endpoints(
            asset.getEndpoints().stream()
                .map(
                    endpoint ->
                        Endpoint.builder()
                            .interfaceType(endpoint.getInterfaceType())
                            .protocolInformation(
                                ProtocolInformation.builder()
                                    .endpointAddress(
                                        endpoint.getProtocolInformation().getEndpointAddress())
                                    .endpointProtocolVersion(
                                        endpoint
                                            .getProtocolInformation()
                                            .getEndpointProtocolVersion())
                                    .endpointProtocol(
                                        endpoint.getProtocolInformation().getEndpointProtocol())
                                    .build())
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
