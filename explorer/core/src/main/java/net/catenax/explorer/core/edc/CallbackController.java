package net.catenax.explorer.core.edc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/callback")
public class CallbackController {

  @PostMapping("/endpoint-data-reference")
  public void callback(EndpointDataReference dataReference) {
    log.info("CALLBACK HIT: " + dataReference);

  }


}
