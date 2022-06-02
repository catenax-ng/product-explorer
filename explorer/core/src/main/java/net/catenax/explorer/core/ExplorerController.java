package net.catenax.explorer.core;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/asset/")
@RequiredArgsConstructor
public class ExplorerController {

  final ExplorerService explorerService;

  @GetMapping("{id}")
  public ResponseEntity<AssetData> retrieve(@PathVariable final String id) {
    return ResponseEntity.ok(explorerService.fetchAssetById(id));
  }
}