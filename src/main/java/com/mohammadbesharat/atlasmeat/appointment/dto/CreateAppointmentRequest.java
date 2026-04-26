package com.mohammadbesharat.atlasmeat.appointment.dto;

import com.mohammadbesharat.atlasmeat.appointment.domain.ContactType;
import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateAppointmentRequest(

        @NotBlank(message = "customer name is required")
        @Size(max = 100, message = "customer name must be less than or equal to 100 characters")
        String customerName,
        @NotBlank (message = "customer email is required")
        @Email(message = "customer email must be a valid email")

        String customerEmail,
        @NotBlank (message = "customer phone is required")
        @Size(max = 12, message = "customer phone must be less than or equal to 12 characters")
        String customerPhone,
        @NotNull(message = "contact preference is required")
        ContactType contactPreference,
        @NotNull(message = "animal type is required")
        AnimalType animalType,
        @Min(value = 1, message = "animal count must be at least 1")
        int animalCount,
        LocalDate scheduledDate
) {}
