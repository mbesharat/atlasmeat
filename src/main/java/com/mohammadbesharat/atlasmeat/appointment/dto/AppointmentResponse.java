package com.mohammadbesharat.atlasmeat.appointment.dto;

import com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus;
import com.mohammadbesharat.atlasmeat.appointment.domain.ContactType;
import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;

import java.time.LocalDate;

public record AppointmentResponse(
        Long id,
        String customerName,
        String customerPhone,
        String customerEmail,
        ContactType contactPreference,
        AnimalType animalType,
        int animalCount,
        LocalDate scheduledDate,
        AppointmentStatus status,
        Long checkoutId

) {}
