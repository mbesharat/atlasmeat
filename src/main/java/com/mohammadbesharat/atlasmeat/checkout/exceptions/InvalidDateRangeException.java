package com.mohammadbesharat.atlasmeat.checkout.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateRangeException extends RuntimeException {

     public InvalidDateRangeException(String message){
        super (message);
    }
    
    public InvalidDateRangeException(){
        super("From must be on or before to");
    }
}
