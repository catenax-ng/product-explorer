package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.EdcClient.ContractAgreementWrapper;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Slf4j
public class EdcAssetProvider implements ShellDescriptorProvider {

  private final EdcContractService edcContractService;
  private final EdcTransferService edcTransferService;
  private final String consumerControlPlaneUrl;

  @Override
  public ShellDescriptorResponse search(String query, String providerControlPlaneUrl) {
    ContractAgreementWrapper contractAgreement = edcContractService.negotiateContractForAssetId(query,
        consumerControlPlaneUrl, providerControlPlaneUrl);

    if (contractAgreement == null) {
      throw new ResourceNotFoundException(query);
    }

    log.info("Found contract agreement:" + contractAgreement);

    TransferProcess transferProcess = edcTransferService.initializeHttpTransferProcess(
        contractAgreement.contractAgreementId(), query, consumerControlPlaneUrl, providerControlPlaneUrl);

    return null;
  }
}