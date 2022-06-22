package net.catenax.explorer.core.edc.exception;

public class ContractNegotiationException extends RuntimeException{
  public ContractNegotiationException(final String message) {
    super("Contract negotiation failed due to %s".formatted(message));
  }
}
