package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidCheckoutStatusTransitionException extends RuntimeException {

     public InvalidCheckoutStatusTransitionException(String message){
        super (message);
    }
    
    public InvalidCheckoutStatusTransitionException(CheckoutStatus current, CheckoutStatus newStatus){
        super(current + " cannot be changed to " + newStatus);
    }
}