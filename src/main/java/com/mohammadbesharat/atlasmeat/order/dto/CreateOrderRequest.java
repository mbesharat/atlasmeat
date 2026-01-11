package com.mohammadbesharat.atlasmeat.order.dto;

import jakarta.validation.constraints.*;

public record CreateOrderRequest(
    
    @NotBlank (message = "customer name is required")
    @Size (max = 100, message = "customer name must be less than or equal to 100 characters")
    String customerName,
    @NotBlank (message = "customer email is required")
    @Email (message = "customer email must be a valid email")
    String customerEmail,
    @NotBlank (message = "customer phone is required")
    @Size (max = 30, message = "customer phone must be equal to or else than 30 characters")
    String customerPhone,
    @NotBlank (message = "order details is required")
    @Size (max = 2000, message = "order details must be equal to or less than 2k characters")
    String orderDetails
)
{}
