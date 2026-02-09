package com.mohammadbesharat.atlasmeat.checkout.dto;

import java.util.List;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;

import jakarta.validation.Valid;

public record UpdateOrderRequest(
    AnimalType animal,
    @Valid List<CreateOrderItemRequest> items
) {}
