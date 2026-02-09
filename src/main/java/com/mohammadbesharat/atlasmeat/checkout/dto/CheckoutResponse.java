package com.mohammadbesharat.atlasmeat.checkout.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;

public record CheckoutResponse(

    Long checkoutId,
    String customerName,
    String customerPhone,
    String customerEmail,
    CheckoutStatus status,
    LocalDateTime createdAt,
    List<OrderResponse> orders

) {}
