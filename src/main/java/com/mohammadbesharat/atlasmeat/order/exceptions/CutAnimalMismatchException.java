package com.mohammadbesharat.atlasmeat.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;

@ResponseStatus(HttpStatus.CONFLICT)
public class CutAnimalMismatchException extends RuntimeException {

     public CutAnimalMismatchException(String message){
        super (message);
    }
    
    public CutAnimalMismatchException(Long id, AnimalType animal){
        super("Cut " + id + "does not belong to " + animal);
    }
}