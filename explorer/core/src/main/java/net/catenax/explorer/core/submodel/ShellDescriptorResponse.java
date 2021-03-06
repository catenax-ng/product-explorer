package net.catenax.explorer.core.submodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShellDescriptorResponse {

  List<ShellDescriptor> items;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ShellDescriptor {
    List<Description> description;
    GlobalAssetId globalAssetId;
    String idShort;
    String identification;
    List<SpecificAssetIds> specificAssetIds;
    List<SubmodelDescriptor> submodelDescriptors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Description {
      Locale language;
      String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GlobalAssetId {
      List<String> value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecificAssetIds {
      String key;
      String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SubmodelDescriptor {
      List<Description> description;
      String idShort;
      String identification;
      SemanticId semanticId;
      List<Endpoint> endpoints;

      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      static class SemanticId {
        List<String> value;
      }

      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      static class Endpoint {

        @JsonProperty("interface")
        String interfaceName;
        ProtocolInformation protocolInformation;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class ProtocolInformation {
          String endpointAddress;
          String endpointProtocol;
          String endpointProtocolVersion;
        }
      }
    }
  }
}
