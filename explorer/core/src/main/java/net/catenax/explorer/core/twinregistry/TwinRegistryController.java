package net.catenax.explorer.core.twinregistry;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.twinregistry.TwinRegistryService.SearchShellDescriptorResults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("v1/assets/")
@RequiredArgsConstructor
@Slf4j
public class TwinRegistryController {

  final TwinRegistryService twinRegistryService;

  @GetMapping("{query}")
  public ResponseEntity<List<SearchShellDescriptorResults>> retrieve(@PathVariable final String query, @RequestParam(required = false) String bpn) {
    log.info("Querying for Asset by: " + query);
    return ResponseEntity.ok(twinRegistryService.searchTwinRegistries(query, bpn));
  }
}