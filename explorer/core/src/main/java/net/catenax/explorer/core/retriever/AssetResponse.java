package net.catenax.explorer.core.retriever;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AssetResponse {

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
}