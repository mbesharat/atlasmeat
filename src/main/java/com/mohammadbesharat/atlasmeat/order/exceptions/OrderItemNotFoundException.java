package com.mohammadbesharat.atlasmeat.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemNotFoundException extends RuntimeException {

  public OrderItemNotFoundException(Long orderItemId, Long orderId, Long checkoutId) {
    super("Item not found with Id " + orderItemId
            + " in order with ID " + orderId
            + " in checkout with ID " + checkoutId);
  }

  public OrderItemNotFoundException(String message) {
    super(message);
  }
}