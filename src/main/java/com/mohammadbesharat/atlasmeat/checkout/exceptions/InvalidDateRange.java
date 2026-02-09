package com.mohammadbesharat.atlasmeat.checkout.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateRange extends RuntimeException {

     public InvalidDateRange(String message){
        super (message);
    }
    
    public InvalidDateRange(){
        super("From must be on or before to");
    }
}
