package net.catenax.explorer.core;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.submodel.twinregistry.ShellDescriptorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/assets/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerController {

  final ExplorerService explorerService;

  @GetMapping("{query}")
  public ResponseEntity<ShellDescriptorResponse> retrieve(@PathVariable final String query) {
    log.info("Querying for Asset by: " + query);
    return ResponseEntity.ok(explorerService.search(query));
  }
}