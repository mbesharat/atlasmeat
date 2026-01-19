package com.mohammadbesharat.atlasmeat.order.dto;


import jakarta.validation.constraints.*;

public record CreateOrderItemRequest (

    @NotNull(message = "cut id is required")
    Long cutId,

    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "quantity must be at least one")
    Integer quantity

){}
