package com.mohammadbesharat.atlasmeat.checkout.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateItemRequest (

    @NotNull(message = "Must include quantity")
    @Min(value = 1, message = "quantity must be at least one")
    Integer quantity
)
{}
