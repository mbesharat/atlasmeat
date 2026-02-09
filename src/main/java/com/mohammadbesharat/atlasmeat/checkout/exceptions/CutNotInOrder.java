package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CutNotInOrder extends RuntimeException {

  public CutNotInOrder(Long orderId, Long cutId) {
    super("Cut not found with id " + cutId + " in order with id " + orderId);
  }

  public CutNotInOrder(String message) {
    super(message);
  }
}
