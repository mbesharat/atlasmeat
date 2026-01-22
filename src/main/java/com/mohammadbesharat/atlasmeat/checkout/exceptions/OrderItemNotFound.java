package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemNotFound extends RuntimeException {

  public OrderItemNotFound(Long orderId, Long checkoutId) {
    super("Order item not found in order with ID " + orderId + " in checkout with ID " + checkoutId);
  }

  public OrderItemNotFound(String message) {
    super(message);
  }
}