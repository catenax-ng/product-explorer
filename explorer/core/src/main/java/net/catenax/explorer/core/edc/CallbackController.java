package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/callback")
public class CallbackController {
    private final EdcClient edcClient;

    @PostMapping("/endpoint-data-reference")
    public ResponseEntity<Object> callback(@RequestBody EndpointDataReference dataReference) {
        log.info("CALLBACK HIT: " + toString(dataReference));
        log.info("DATA: " + edcClient.getData(dataReference));

        return ResponseEntity.ok().build();
    }

    private String toString(EndpointDataReference dataReference) {
        return "EndpointDataReference{" +
                "id='" + dataReference.getId() + '\'' +
                ", endpoint='" + dataReference.getEndpoint() + '\'' +
                ", authKey='" + dataReference.getAuthKey() + '\'' +
                ", authCode='" + dataReference.getAuthCode() + '\'' +
                ", properties=" + dataReference.getProperties() +
                '}';
    }
}
