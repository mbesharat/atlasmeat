package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CutNotFound extends RuntimeException {

  public CutNotFound(Long id) {
    super("Cut not found with id " + id);
  }

  public CutNotFound(String message) {
    super(message);
  }
}
