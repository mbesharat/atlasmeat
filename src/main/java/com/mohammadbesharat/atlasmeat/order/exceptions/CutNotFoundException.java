package com.mohammadbesharat.atlasmeat.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CutNotFoundException extends RuntimeException {

  public CutNotFoundException(Long id) {
    super("Cut not found with id " + id);
  }

  public CutNotFoundException(String message) {
    super(message);
  }
}
