package com.mohammadbesharat.atlasmeat.checkout.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateItemRequest (

    @NotNull(message = "Must include quantity")
    Integer quantity
)
{}
