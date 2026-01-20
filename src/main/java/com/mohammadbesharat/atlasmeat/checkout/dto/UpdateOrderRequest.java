package com.mohammadbesharat.atlasmeat.checkout.dto;

import java.util.List;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;

public record UpdateOrderRequest(
    AnimalType animal,
    List<CreateOrderItemRequest> items
) {}
