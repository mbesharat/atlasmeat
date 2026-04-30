package com.mohammadbesharat.atlasmeat.appointment.dto;

import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAppointmentStatusRequest(
    @NotNull(message = "Status is required")
    AppointmentStatus status
){}
