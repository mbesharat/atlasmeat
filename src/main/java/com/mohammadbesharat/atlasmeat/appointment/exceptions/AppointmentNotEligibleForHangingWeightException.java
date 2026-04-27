package com.mohammadbesharat.atlasmeat.appointment.exceptions;

import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;

public class AppointmentNotEligibleForHangingWeightException extends RuntimeException{

    public AppointmentNotEligibleForHangingWeightException(String message){
        super(message);
    }

    public AppointmentNotEligibleForHangingWeightException(Long id, AppointmentStatus status){
        super("Appointment with id " + id + " with status " + status + " cannot have hanging weight set.");
    }
}
