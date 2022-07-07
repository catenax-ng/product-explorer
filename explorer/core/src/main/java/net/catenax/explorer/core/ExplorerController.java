package net.catenax.explorer.core;

import java.time.Duration;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse;
import net.catenax.explorer.core.submodel.ShellDescriptorResponse.ShellDescriptor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@RequestMapping("v1/assets/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerController {

  final ExplorerService explorerService;

//  @GetMapping("{query}")
//  public ResponseEntity<ShellDescriptorResponse> retrieve(@PathVariable final String query) {
//    log.info("Querying for Asset by: " + query);
//    return ResponseEntity.ok(explorerService.search(query));
//  }

  @GetMapping(path = "/stream/{query}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<ShellDescriptor> retrieveFlux(@PathVariable final String query) {
    log.info("Querying for Asset by: " + query);
    return explorerService.search(query);
  }

}