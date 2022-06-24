package net.catenax.explorer.core.edc;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.EdcClient.ContractAgreementWrapper;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;

@RequiredArgsConstructor
@Slf4j
public class EdcAssetProvider implements ShellDescriptorProvider {

  private final EdcContractService edcContractService;
  private final EdcTransferService edcTransferService;
  private final String consumerControlPlaneUrl;
  private final Map<String, EndpointDataReference> cache = new HashMap<>();

  @Override
  @SneakyThrows
  public ShellDescriptorResponse search(String query, String providerControlPlaneUrl) {
    ContractAgreementWrapper contractAgreement = edcContractService.negotiateContractForAssetId(query,
        consumerControlPlaneUrl, providerControlPlaneUrl);

    if (contractAgreement == null) {
      throw new ResourceNotFoundException(query);
    }

    log.info("Found contract agreement:" + contractAgreement);

    edcTransferService.initializeHttpTransferProcess(contractAgreement.contractAgreementId(), query, consumerControlPlaneUrl, providerControlPlaneUrl);

    while (!cache.containsKey(contractAgreement.contractAgreementId())) {
      Thread.sleep(1000);
    }

    return edcTransferService.getData(cache.get(contractAgreement.contractAgreementId()));
  }

  @Override
  public void persistCallback(EndpointDataReference endpointDataReference) {
    cache.put(endpointDataReference.getProperties().get("cid"), endpointDataReference);
  }
}