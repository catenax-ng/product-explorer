package net.catenax.explorer.selfdescriptionhub.web;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/self-desc-hub/")
public class MockController {

  @Value("#{'${known.edc.list}'.split(',')}")
  private List<String> knownEdcs;

  @GetMapping
  public List<SelfDescription> retrieve() {

    return knownEdcs.stream()
        .map(knownEdc -> SelfDescription.builder()
            .companyNumber(RandomStringUtils.randomNumeric(10))
            .serviceProvider(knownEdc)
            .bpn(knownEdc.substring(knownEdc.lastIndexOf("-") + 1, knownEdc.lastIndexOf(":")))
            .build())
        .collect(Collectors.toList());
  }
}
