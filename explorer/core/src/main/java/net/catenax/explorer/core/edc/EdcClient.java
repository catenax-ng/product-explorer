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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class EdcClient {

  private static final String IDS_PATH = "/api/v1/ids/data";

  private static final String CONTRACT_NEGOTIATION_PATH = "api/v1/data/contractnegotiations"; // move from here to the Service

  private static final ParameterizedTypeReference<Catalog> OFFER_REFERENCE = new ParameterizedTypeReference<>() {};

  private final WebClient webClient;

  public ContractOffer findContractOfferForAssetId(String assetId, String endpointAddress) {
    Catalog catalog = webClient.get()
        .uri(endpointAddress + IDS_PATH)
        .header("X-Api-Key", "apipassword")
        .retrieve()
        .bodyToMono(OFFER_REFERENCE)
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
    ContractAgreementWrapper result = webClient.get()
        .uri(endpointAddress + CONTRACT_NEGOTIATION_PATH + "/" + contractNegotiationId)
        .header("X-Api-Key", "apipassword")
        .retrieve()
        .bodyToMono(ContractAgreementWrapper.class)
        .block(Duration.ofSeconds(5));

    return result;
  }

  public TransferProcess initializeHttpTransferProcess(TransferRequestDto request, String endpointAddress) {
    TransferProcess result = webClient.post()
            .uri(endpointAddress)
            .header("X-Api-Key", "apipassword")
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(TransferProcess.class)
            .block(Duration.ofSeconds(5));

    return result;
  }

  public String getData(EndpointDataReference endpointDataReference) {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.set(endpointDataReference.getAuthKey(), endpointDataReference.getAuthCode());

      HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

      ResponseEntity<String> response1 = restTemplate.exchange(
              endpointDataReference.getEndpoint(), HttpMethod.GET, requestEntity, String.class);
      return response1.getBody();

      /** nie wiem jak dziala ten webClient, zwraca body=null **/
//    String response = webClient.get()
//            .uri(endpointDataReference.getEndpoint())
//            .header(endpointDataReference.getAuthKey(), endpointDataReference.getAuthCode())
//            .retrieve()
//            .bodyToMono(String.class)
//            .block();
//
//    return response;
  }

  record ContractIdWrapper (String id) {}

  record ContractAgreementWrapper(String id, String contractAgreementId, String state) {}
}
