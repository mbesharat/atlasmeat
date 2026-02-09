package com.mohammadbesharat.atlasmeat.order.dto;

import java.util.List;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;

public record OrderResponse(

    Long id,
    AnimalType animal,
    List<OrderItemResponse> items

) {}
