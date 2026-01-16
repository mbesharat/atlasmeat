package com.mohammadbesharat.atlasmeat.order.dto;


import jakarta.validation.constraints.*;

public record CreateOrderItemRequest (

    @NotNull(message = "cut id is required")
    Long cutId,

    @NotNull(message = "quantity is required")
    @Size(min = 1, message = "quantity must be at least one")
    Integer quantity

){}
