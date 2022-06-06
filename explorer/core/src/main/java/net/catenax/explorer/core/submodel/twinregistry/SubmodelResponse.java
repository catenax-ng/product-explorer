package net.catenax.explorer.core.submodel.twinregistry;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Locale;
import lombok.Data;
import lombok.Value;
import net.catenax.explorer.core.submodel.twinregistry.SubmodelResponse.Items.GlobalAssetId;
import net.catenax.explorer.core.submodel.twinregistry.SubmodelResponse.Items.SpecificAssetIds;

@Value
public class SubmodelResponse {

  List<Items> items;
  GlobalAssetId globalAssetId;
  String idShort;
  String identification;
  List<SpecificAssetIds> specificAssetIds;
//  {
//    "idShort": "future concept x",
//      "identification": "459842bf-3466-4eb6-8d95-ef0557e64883"
//  } todo missing

  @Value
  static class Items {
    List<Description> description;

    @Value
    static class Description {
      Locale language;
      String text;
    }

    @Value
    static class GlobalAssetId {
      List<String> value;
    }

    @Value
    static class SpecificAssetIds {
      String key;
      String value;
    }

    @Value
    static class SubmodelDescriptor {
      private List<Description> description;
      private String idShort;
      private String identification;
      private SemanticId semanticId;

      @Value
      static class SemanticId {
        List<String> value;
      }

      @Value
      static class Endpoint {

        @JsonProperty("interface")
        private String interfaceName;

        @Value
        static class ProtocolInformation {
          String endpointAddress;
          String endpointProtocol;
          String endpointProtocolVersion;
        }
      }
    }
  }
}