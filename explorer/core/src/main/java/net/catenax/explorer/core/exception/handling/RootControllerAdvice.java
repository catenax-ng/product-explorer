package net.catenax.explorer.core.exception.handling;

import lombok.extern.slf4j.Slf4j;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class RootControllerAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ResourceNotFound handle(ResourceNotFoundException exception) {
    log.debug("Resource not found: ", exception);
    return new ResourceNotFound(exception.getMessage());
  }
}