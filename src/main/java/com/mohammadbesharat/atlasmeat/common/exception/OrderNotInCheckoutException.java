package com.mohammadbesharat.atlasmeat.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotInCheckoutException extends RuntimeException {

  public OrderNotInCheckoutException(Long orderId, Long checkoutId) {
    super("Order not found with id " + orderId + " in checkout " + checkoutId);
  }

  public OrderNotInCheckoutException(String message) {
    super(message);
  }
}
