package net.catenax.explorer.core.edc;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.edc.model.TransferRequestDto;
import org.eclipse.dataspaceconnector.spi.types.domain.DataAddress;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferType;

@Slf4j
public class EdcTransferService {

  private static final String IDS_DATA_PATH = "/api/v1/ids/data";
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
        .contentType(APPLICATION_OCTET_STREAM_VALUE)
        .isFinite(true)
        .build();

    TransferRequestDto transferRequest = TransferRequestDto.Builder.newInstance()
        .assetId(query)
        .contractId(contractAgreementId)
        .connectorId("provider")
        .connectorAddress(providerUrl + IDS_DATA_PATH)
        .protocol("ids-multipart")
        .dataDestination(dataDestination)
        .managedResources(false)
        .transferType(transferType)
        .build();

    TransferProcess process = edcClient
        .initializeHttpTransferProcess(transferRequest, endpointAddress + TRANSFER_PATH);

    log.info("Received transfer id process: " + process.getId());

    return process;
  }

  public String getData(EndpointDataReference endpointDataReference) {
    return edcClient.getData(endpointDataReference);
  }
}