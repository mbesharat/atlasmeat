package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CheckoutLockedException extends RuntimeException {

     public CheckoutLockedException(String message){
        super (message);
    }
    
    public CheckoutLockedException(CheckoutStatus status){
        super("Cannot add orders when checkout status is " + status);
    }
}
