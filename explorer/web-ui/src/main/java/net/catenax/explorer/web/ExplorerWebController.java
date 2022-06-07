package net.catenax.explorer.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ExplorerWebController {

  private final String explorerApplicationUrl;

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("explorerApplicationUrl", explorerApplicationUrl);
    return "index";
  }
}
