package com.mohammadbesharat.atlasmeat.order.dto;

import java.util.List;

import com.mohammadbesharat.atlasmeat.order.domain.CattleType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateOrderRequest(
    
    @NotNull(message = "cattle type is required")
    CattleType cattleType,
    
    @NotNull(message = "items are required")
    @Size(min = 1, message = "at least one item is required")
    @Valid
    List<CreateOrderItemRequest> items
)
{}
