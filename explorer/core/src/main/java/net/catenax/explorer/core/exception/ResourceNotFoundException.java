package net.catenax.explorer.core.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(final String id) {
    super("Resource not found for id %s".formatted(id));
  }
}