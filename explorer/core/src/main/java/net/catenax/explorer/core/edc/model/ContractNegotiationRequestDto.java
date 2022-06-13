package net.catenax.explorer.core.edc.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ContractNegotiationRequestDto {

  String connectorAddress;
  String protocol;
  String connectorId;
  ContractOfferDescription offer;

}
