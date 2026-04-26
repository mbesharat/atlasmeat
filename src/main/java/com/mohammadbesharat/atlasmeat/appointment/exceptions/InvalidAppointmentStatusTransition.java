package com.mohammadbesharat.atlasmeat.appointment.exceptions;

import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidAppointmentStatusTransition extends RuntimeException {

    public InvalidAppointmentStatusTransition(String message){
        super (message);
    }

    public InvalidAppointmentStatusTransition(AppointmentStatus current, AppointmentStatus newStatus){
        super(current + " cannot be changed to " + newStatus);
    }
}
