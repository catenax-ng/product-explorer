package net.catenax.explorer.core.edc;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.model.ContractNegotiationRequestDto;
import net.catenax.explorer.core.edc.model.TransferRequestDto;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import org.eclipse.dataspaceconnector.spi.types.domain.catalog.Catalog;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.offer.ContractOffer;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class EdcClient {

  private static final String IDS_PATH = "/api/v1/ids/data";

  private static final String CONTRACT_NEGOTIATION_PATH = "api/v1/data/contractnegotiations"; // move from here to the Service

  private final WebClient webClient;

  public ContractOffer findContractOfferForAssetId(String assetId, String endpointAddress) {
    Catalog catalog = webClient.get()
        .uri(endpointAddress + IDS_PATH)
        .header("X-Api-Key", "apipassword")
        .retrieve()
        .bodyToMono(Catalog.class)
        .block(Duration.ofSeconds(5));

    log.info("Got Catalog from provider:" + catalog);

    if (catalog.getContractOffers().isEmpty()) {
      throw new ResourceNotFoundException("Provider's Contract Offers Catalog is empty.");
    }

    return catalog.getContractOffers()
        .stream()
        .filter(it -> it.getAsset().getId().equals(assetId))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("Could not find Contract Offer for given Asset Id"));
  }

  public String initializeContractNegotiation(ContractNegotiationRequestDto request, String endpointAddress) {
    ContractIdWrapper contractNegotiation = webClient.post()
        .uri(endpointAddress + CONTRACT_NEGOTIATION_PATH)
        .header("X-Api-Key", "apipassword")
        .header("Content-Type", "application/json")
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(ContractIdWrapper.class)
        .block(Duration.ofSeconds(5));

    log.info("Got Contract Negotiation: " + contractNegotiation);

    return contractNegotiation.id();
  }

  public ContractAgreementWrapper getContractNegotiation(String contractNegotiationId, String endpointAddress) {
    return webClient.get()
        .uri(endpointAddress + CONTRACT_NEGOTIATION_PATH + "/" + contractNegotiationId)
        .header("X-Api-Key", "apipassword")
        .retrieve()
        .bodyToMono(ContractAgreementWrapper.class)
        .block(Duration.ofSeconds(5));
  }

  public TransferProcess initializeHttpTransferProcess(TransferRequestDto request, String endpointAddress) {
    return webClient.post()
            .uri(endpointAddress)
            .header("X-Api-Key", "apipassword")
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(TransferProcess.class)
            .block(Duration.ofSeconds(5));
  }

  public String getData(EndpointDataReference endpointDataReference) {
    return webClient.get()
        .uri(endpointDataReference.getEndpoint())
        .header(endpointDataReference.getAuthKey(), endpointDataReference.getAuthCode())
        .retrieve()
        .bodyToMono(String.class)
        .block(Duration.ofSeconds(10));
  }

  record ContractIdWrapper (String id) {}

  record ContractAgreementWrapper(String id, String contractAgreementId, String state) {}

}
