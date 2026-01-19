package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class CheckoutNotFound extends RuntimeException{
    
    public CheckoutNotFound(String message){
        super (message);
    }
    
    public CheckoutNotFound(Long id){
        super("Checkout not found with id " + id);
    }
}
