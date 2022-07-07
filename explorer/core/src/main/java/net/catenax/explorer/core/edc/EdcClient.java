package net.catenax.explorer.core.edc;

import static java.time.Duration.ofSeconds;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.exception.ContractNegotiationException;
import net.catenax.explorer.core.edc.model.ContractNegotiationRequestDto;
import net.catenax.explorer.core.edc.model.TransferRequestDto;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import org.eclipse.dataspaceconnector.spi.types.domain.catalog.Catalog;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.offer.ContractOffer;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
public class EdcClient {

  private static final String IDS_PATH = "/api/v1/ids/data";
  private static final String HEADER_X_API_KEY = "X-Api-Key";
  private static final String API_PASSWORD = "apipassword"; //TODO when authorization known remove

  private final WebClient webClient;

  public ContractOffer findContractOfferForAssetId(String assetId, String endpointAddress) {
    Catalog catalog = webClient.get()
        .uri(endpointAddress + IDS_PATH)
        .header(HEADER_X_API_KEY, API_PASSWORD)
        .retrieve()
        .bodyToMono(Catalog.class)
        .block(ofSeconds(10));

    log.info("Got Catalog with number of offers: " + catalog.getContractOffers().size());

    if (isNull(catalog) || catalog.getContractOffers().isEmpty()) {
      throw new ResourceNotFoundException("Provider's Contract Offers Catalog is empty.");
    }

    return catalog.getContractOffers()
        .stream()
        .filter(it -> assetId.equals(it.getAsset().getId()))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Could not find Contract Offer for given Asset Id"));
  }

  public String initializeContractNegotiation(ContractNegotiationRequestDto request, String endpointAddress) {
    ContractIdWrapper contractNegotiation = webClient.post()
        .uri(endpointAddress)
        .header(HEADER_X_API_KEY, API_PASSWORD)
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(ContractIdWrapper.class)
        .block(ofSeconds(10));

    log.info("Got Contract Negotiation: " + contractNegotiation);

    if (isNull(contractNegotiation)) {
      throw new ContractNegotiationException("Could not initialize contract negotiation for Asset: " + request.getOffer().getAssetId());
    }
    return contractNegotiation.id();
  }

  public ContractAgreementWrapper getContractAgrement(String contractNegotiationId, String endpointAddress) {
    return webClient.get()
        .uri(endpointAddress + "/" + contractNegotiationId)
        .header(HEADER_X_API_KEY, API_PASSWORD)
        .retrieve()
        .bodyToMono(ContractAgreementWrapper.class)
        .block(ofSeconds(10));
  }

  public TransferProcess initializeHttpTransferProcess(TransferRequestDto request, String endpointAddress) {
    return webClient.post()
        .uri(endpointAddress)
        .header(HEADER_X_API_KEY, API_PASSWORD)
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(TransferProcess.class)
        .block(ofSeconds(10));
  }

  public Flux<ShellDescriptor> getData(EndpointDataReference endpointDataReference) {
    return webClient.get()
        .uri(endpointDataReference.getEndpoint())
        .header(endpointDataReference.getAuthKey(), endpointDataReference.getAuthCode())
        .retrieve()
        .bodyToFlux(ShellDescriptor.class);
  }

  record ContractIdWrapper(String id) {

  }

  record ContractAgreementWrapper(String id, String contractAgreementId, String state) {

  }
}
