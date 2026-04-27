package com.mohammadbesharat.atlasmeat.appointment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException(Long id) {
        super("Appointment not found with id " + id);
    }
}
