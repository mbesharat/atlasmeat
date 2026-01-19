package com.mohammadbesharat.atlasmeat.checkout.dto;

import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateCheckoutRequest(

    @NotBlank (message = "customer name is required")
    @Size (max = 100, message = "customer name must be less than or equal to 100 characters")
    String customerName,
    @NotBlank (message = "customer email is required")
    @Email (message = "customer email must be a valid email")
    String customerEmail,
    @NotBlank (message = "customer phone is required")
    @Size (max = 30, message = "customer phone must be equal to or less than 30 characters")
    String customerPhone,
    @NotNull(message = "orders are required")
    @Size(min = 1, message = "at least one order is required")
    @Valid
    List<CreateOrderRequest> orders

){}
