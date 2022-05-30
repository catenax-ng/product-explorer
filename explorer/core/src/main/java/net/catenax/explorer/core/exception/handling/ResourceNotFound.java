package net.catenax.explorer.core.exception.handling;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResourceNotFound {

  private String code = "RESOURCE_NOT_FOUND";
  private String message = "Resource not found";

  public ResourceNotFound(final String message) {
    this.message = message;
  }
}