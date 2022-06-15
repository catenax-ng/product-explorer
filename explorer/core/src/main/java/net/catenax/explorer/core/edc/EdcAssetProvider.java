package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.EdcClient.ContractAgreementWrapper;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;

@RequiredArgsConstructor
@Slf4j
public class EdcAssetProvider implements ShellDescriptorProvider {

  private final EdcContractService edcContractService;

  private final EdcTransferService edcTransferService;

  @Override
  public ShellDescriptorResponse search(String query, String providerUrl) {

//    "http://localhost:8111/data/catalog?providerUrl=";
//
//    "http://localhost:8282/api/v1/ids/data";

    String endpointAddress = "http://localhost:8195/"; // /data/catalog?providerUrl=" + providerUrl;

    //"http://localhost:8282" - consumer ids

    ContractAgreementWrapper contractAgreement = edcContractService.findContractOfferForAssetId(query,
        endpointAddress, providerUrl);

    if (contractAgreement == null) {
      throw new ResourceNotFoundException(query);
    }

    log.info("Found contract agreement:" + contractAgreement);

    TransferProcess transferProcess = edcTransferService.initializeHttpTransferProcess(
        contractAgreement.id(), query, endpointAddress, providerUrl);



    return null;
  }
}
