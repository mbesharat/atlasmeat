package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPatchRequest extends RuntimeException {

     public InvalidPatchRequest(String message){
        super (message);
    }
    
    public InvalidPatchRequest(){
        super("Nothing was sent to be updated");
    }
}
