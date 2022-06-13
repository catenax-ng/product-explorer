package net.catenax.explorer.core.edc.model;

import lombok.Builder;
import lombok.Value;
import org.eclipse.dataspaceconnector.policy.model.Policy;

@Value
@Builder
public class ContractOfferDescription {

  String offerId;
  String assetId;
  String policyId;
  Policy policy;

}
