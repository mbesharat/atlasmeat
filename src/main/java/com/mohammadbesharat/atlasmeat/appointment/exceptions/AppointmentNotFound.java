package com.mohammadbesharat.atlasmeat.appointment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppointmentNotFound extends RuntimeException {

    public AppointmentNotFound(String message) {
        super(message);
    }

    public AppointmentNotFound(Long id) {
        super("Appointment not found with id " + id);
    }
}
