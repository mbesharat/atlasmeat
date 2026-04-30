package com.mohammadbesharat.atlasmeat.appointment.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SetScheduledDateRequest(
        @NotNull(message = "scheduled date is required")
        LocalDate scheduledDate
)
{}
