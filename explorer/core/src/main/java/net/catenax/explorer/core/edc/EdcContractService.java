package net.catenax.explorer.core.edc;

import static java.util.Objects.*;

import java.util.Objects;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.EdcClient.ContractAgreementWrapper;
import net.catenax.explorer.core.edc.exception.ContractNegotiationException;
import net.catenax.explorer.core.edc.model.ContractNegotiationRequestDto;
import net.catenax.explorer.core.edc.model.ContractOfferDescription;
import org.eclipse.dataspaceconnector.policy.model.Action;
import org.eclipse.dataspaceconnector.policy.model.Permission;
import org.eclipse.dataspaceconnector.policy.model.Policy;
import org.eclipse.dataspaceconnector.policy.model.PolicyType;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.agreement.ContractAgreement;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.offer.ContractOffer;

@RequiredArgsConstructor
@Slf4j
public class EdcContractService {

  private static final String CONTRACT_NEGOTIATION_PATH = "/api/v1/data/contractnegotiations";
  private static final String DATA_CATALOG_PROVIDER_URL = "/api/v1/data/catalog?providerUrl=";
  private static final String STATUS_CONFIRMED = "CONFIRMED";
  private static final String IDS_DATA_PATH = "/api/v1/ids/data";

  private final EdcClient edcClient;

  @SneakyThrows
  public ContractAgreementWrapper negotiateContractForAssetId(String assetId, String consumerControlPlaneUrl, String providerControlPlaneUrl) {
    ContractOffer contractOffer = edcClient.findContractOfferForAssetId(assetId, consumerControlPlaneUrl + DATA_CATALOG_PROVIDER_URL + providerControlPlaneUrl);

    String contractNegotiationId = initializeContractNegotiation(contractOffer, consumerControlPlaneUrl, providerControlPlaneUrl);

    ContractAgreementWrapper response;

//    int retries = 10;
    do {
      response = edcClient.getContractAgrement(contractNegotiationId,
          consumerControlPlaneUrl + CONTRACT_NEGOTIATION_PATH);
      Thread.sleep(1000);
//      retries--;
    } while (STATUS_CONFIRMED.equals(response.state()));

    if (isNull(response)) {//TODO should be ContractRejection?
      throw new ContractNegotiationException("Failed to negotiate contract for Asset: " + assetId);
    }

    log.info("Contract negotiated: " + response);

    return response;
  }

  private String initializeContractNegotiation(ContractOffer contractOffer, String consumerControlPlaneUrl, String providerControlPlaneUrl) {
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
        .policy(policy)
        .build();

    ContractNegotiationRequestDto request = ContractNegotiationRequestDto.builder()
        .connectorAddress(providerControlPlaneUrl + IDS_DATA_PATH)// TODO should be in ContractOffer?
        .connectorId("provider")// TODO should be in ContractOffer?
        .offer(contractOfferDescription)
        .protocol("ids-multipart")// TODO should be in ContractOffer?
        .build();

    return edcClient.initializeContractNegotiation(request,
        consumerControlPlaneUrl + CONTRACT_NEGOTIATION_PATH);
  }
}