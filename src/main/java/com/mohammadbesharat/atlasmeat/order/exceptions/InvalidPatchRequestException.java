package com.mohammadbesharat.atlasmeat.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPatchRequestException extends RuntimeException {

     public InvalidPatchRequestException(String message){
        super (message);
    }
    
    public InvalidPatchRequestException(){
        super("Nothing was sent to be updated");
    }
}
