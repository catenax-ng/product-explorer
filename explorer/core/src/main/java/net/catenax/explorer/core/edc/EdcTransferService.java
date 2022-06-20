package net.catenax.explorer.core.edc;

import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.model.TransferRequestDto;
import org.eclipse.dataspaceconnector.spi.types.domain.DataAddress;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferType;

@Slf4j
public class EdcTransferService {

  private static final String TRANSFER_PATH = "/api/v1/data/transferprocess";

  private final EdcClient edcClient;

  public EdcTransferService(EdcClient edcClient) {
    this.edcClient = edcClient;
  }

  public TransferProcess initializeHttpTransferProcess(String contractAgreementId, String query, String endpointAddress, String providerUrl) {

    DataAddress dataDestination = DataAddress.Builder.newInstance()
        .type("HttpProxy")
        .build();

    TransferType transferType = TransferType.Builder.
        transferType()
        .contentType("application/octet-stream")
        .isFinite(true)
        .build();

    TransferRequestDto transferRequest = TransferRequestDto.Builder.newInstance()
        .assetId(query)
        .contractId(contractAgreementId)
        .connectorId("provider")
        .connectorAddress("http://provider-controlplane:8282/api/v1/ids/data") //TODO(mkizlich): extract
        .protocol("ids-multipart")
        .dataDestination(dataDestination)
        .managedResources(false)
        .transferType(transferType)
        .build();

    TransferProcess process = edcClient
        .initializeHttpTransferProcess(transferRequest, endpointAddress + TRANSFER_PATH);

    log.info("TRANSFER PROCESS ID: " + process.getId());

    return process;

  }
}
