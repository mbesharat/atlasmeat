package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class CheckoutNotFoundException extends RuntimeException{
    
    public CheckoutNotFoundException(String message){
        super (message);
    }
    
    public CheckoutNotFoundException(Long id){
        super("Checkout not found with id " + id);
    }
}
