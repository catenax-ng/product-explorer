package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.shell.ShellDescriptorResponse.ShellDescriptor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@RequestMapping("v1/assets/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerController {

  final ExplorerService explorerService;

  @PostMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public @ResponseBody Flux<ShellDescriptor> search(@RequestBody final QueryCommand command) {
    log.info("Querying for Asset by query: " + command);
    return explorerService.search(command);
  }
}
