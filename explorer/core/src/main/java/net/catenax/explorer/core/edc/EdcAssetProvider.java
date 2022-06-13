package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.eclipse.dataspaceconnector.spi.types.domain.contract.offer.ContractOffer;

@RequiredArgsConstructor
@Slf4j
public class EdcAssetProvider implements ShellDescriptorProvider {

  private final EdcContractService edcContractService;

  @Override
  public ShellDescriptorResponse search(String query, String providerUrl) {

//    "http://localhost:8111/data/catalog?providerUrl=";
//
//    "http://localhost:8282/api/v1/ids/data";

    String endpointAddress = "http://localhost:8110"; // /data/catalog?providerUrl=" + providerUrl;

    //"http://localhost:8282" - consumer ids

    ContractOffer contractOffer = edcContractService.findContractOfferForAssetId(query,
        endpointAddress, providerUrl);

    if (contractOffer == null) {
      throw new ResourceNotFoundException(query);
    }

    log.info("Found contract offer:" + contractOffer);

    return null;
  }
}
