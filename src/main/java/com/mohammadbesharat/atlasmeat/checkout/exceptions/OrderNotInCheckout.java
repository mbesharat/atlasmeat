package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotInCheckout extends RuntimeException {

  public OrderNotInCheckout(Long checkoutId, Long orderId) {
    super("Order not found with id " + orderId + " in checkout " + checkoutId);
  }

  public OrderNotInCheckout(String message) {
    super(message);
  }
}
