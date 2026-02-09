package com.mohammadbesharat.atlasmeat.checkout.dto;

import jakarta.validation.constraints.*;

public record CreateCheckoutRequest(

    @NotBlank (message = "customer name is required")
    @Size (max = 100, message = "customer name must be less than or equal to 100 characters")
    String customerName,
    @NotBlank (message = "customer phone is required")
    @Size (max = 12, message = "customer phone must be less than or equal to 12 characters")
    String customerPhone,
    @NotBlank (message = "customer email is required")
    @Email (message = "customer email must be a valid email")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "customer email must be a valid email"
    )
    String customerEmail
){}
