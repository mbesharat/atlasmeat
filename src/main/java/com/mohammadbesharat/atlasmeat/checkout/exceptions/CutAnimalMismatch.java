package com.mohammadbesharat.atlasmeat.checkout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;

@ResponseStatus(HttpStatus.CONFLICT)
public class CutAnimalMismatch extends RuntimeException {

     public CutAnimalMismatch(String message){
        super (message);
    }
    
    public CutAnimalMismatch(Long id, AnimalType animal){
        super("Cut " + id + "does not belong to " + animal);
    }
}