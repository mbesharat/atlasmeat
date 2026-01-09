package com.mohammadbesharat.atlasmeat.order.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException{
    
    public OrderNotFoundException(Long id){
        super("Order not found with id" + id);
    }
}
