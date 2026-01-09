package com.mohammadbesharat.atlasmeat.order.dto;

import jakarta.validation.constraints.*;

public record CreateOrderRequest(
    
    @NotBlank String customerName,
    @NotBlank @Email String customerEmail,
    @NotBlank String customerPhone,
    @NotBlank String orderDetails
)
{}
