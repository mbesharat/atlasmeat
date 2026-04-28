package com.mohammadbesharat.atlasmeat.appointment.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record SetHangingWeightRequest(
        @NotNull(message = "hanging weight is required")
        @DecimalMin(value = "0.01", message = "hanging weight must be greater than zero")
        @Digits(integer = 5, fraction = 2, message = "hanging weight must have at most 5 digits before decimal and 2 after")
        BigDecimal hangingWeight
){}
