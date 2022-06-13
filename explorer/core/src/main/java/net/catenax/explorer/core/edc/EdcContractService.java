package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.EdcClient.ContractAgreementWrapper;
import net.catenax.explorer.core.edc.model.ContractNegotiationRequestDto;
import net.catenax.explorer.core.edc.model.ContractOfferDescription;
import org.eclipse.dataspaceconnector.policy.model.Action;
import org.eclipse.dataspaceconnector.policy.model.Permission;
import org.eclipse.dataspaceconnector.policy.model.Policy;
import org.eclipse.dataspaceconnector.policy.model.PolicyType;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.offer.ContractOffer;

@RequiredArgsConstructor
@Slf4j
public class EdcContractService {

  private final EdcClient edcClient;

  @SneakyThrows
  public ContractOffer findContractOfferForAssetId(String assetId, String endpointAddress, String providerUrl) {
    ContractOffer contractOffer = edcClient.findContractOfferForAssetId(assetId,
        endpointAddress + "/data/catalog?providerUrl=" + providerUrl);

    String contractNegotiationId = initializeContractNegotiation(contractOffer,
        endpointAddress);

    ContractAgreementWrapper response = null;

    while (response == null || !"CONFIRMED".equals(response.state())) {
      Thread.sleep(1000);
      response = edcClient.getContractNegotiation(contractNegotiationId,
          endpointAddress);
    }

    log.info("Contract negotiated: " + response);

    return null;
  }

  private String initializeContractNegotiation(ContractOffer contractOffer, String endpointAddress) {
    Policy policy = Policy.Builder.newInstance()
        .permission(Permission.Builder.newInstance()
            .target(contractOffer.getAsset().getId())
            .action(Action.Builder.newInstance().type("USE").build())
            .build())
        .type(PolicyType.SET)
        .build();

    ContractOfferDescription contractOfferDescription = ContractOfferDescription.builder()
        .offerId(contractOffer.getId())
        .assetId(contractOffer.getAsset().getId())
        .policyId(null)
        .policy(policy)
        .build();

    ContractNegotiationRequestDto request = ContractNegotiationRequestDto.builder()
        .connectorAddress("http://localhost:8282/api/v1/ids/data")
        .connectorId("provider")
        .offer(contractOfferDescription)
        .protocol("ids-multipart")
        .build();

    String contractNegotiation = edcClient.initializeContractNegotiation(request,
        endpointAddress);

    return contractNegotiation;
  }

}
