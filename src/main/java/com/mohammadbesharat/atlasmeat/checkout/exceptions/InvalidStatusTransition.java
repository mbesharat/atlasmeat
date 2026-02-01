package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStatusTransition extends RuntimeException {

     public InvalidStatusTransition(String message){
        super (message);
    }
    
    public InvalidStatusTransition(CheckoutStatus current, CheckoutStatus newStatus){
        super(current + " cannot be changed to " + newStatus);
    }
}