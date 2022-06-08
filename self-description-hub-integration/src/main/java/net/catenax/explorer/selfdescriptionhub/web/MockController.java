package net.catenax.explorer.selfdescriptionhub.web;

import java.util.List;
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
  public List<String> retrieve() {
    return knownEdcs;
  }

}
