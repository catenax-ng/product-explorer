package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/asset/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerController {

  final ExplorerService explorerService;

  @GetMapping("{query}")
  public ResponseEntity<AssetData> retrieve(@PathVariable final String query) {
    log.info("Querying for Asset by PartNumber: " + query);
    return ResponseEntity.ok(explorerService.search(query));
  }
}


//var userInput
//csv - edc response - DTR
//DTR - url do registry
//GET lookup z {key: PartNumber, value: userInput}
//respons: [id1, id2]
//In loop POST fetch
//response aggrate of submodels