package com.mohammadbesharat.atlasmeat.checkout.dto;

import jakarta.validation.constraints.*;

public record CreateCheckoutRequest(

    @NotBlank (message = "customer name is required")
    @Size (max = 100, message = "customer name must be less than or equal to 100 characters")
    String customerName,
    @NotBlank (message = "customer phone is required")
    @Size (max = 11, message = "customer phone must be less than or equal to 11 characters")
    String customerPhone,
    @NotBlank (message = "customer email is required")
    @Email (message = "customer email must be a valid email")
    String customerEmail
){}
