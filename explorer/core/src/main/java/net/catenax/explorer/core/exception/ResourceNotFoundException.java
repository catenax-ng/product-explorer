package net.catenax.explorer.core.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(final UUID id) {
    super("Resource not found for id %s".formatted(id));
  }
}