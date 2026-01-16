package com.mohammadbesharat.atlasmeat.order.dto;

import java.time.LocalDateTime;

public record OrderResponse(

    Long id,
    String customerName,
    String customerEmail,
    String customerPhone,
    LocalDateTime createdAt,
    LocalDateTime cancelledAt
) {}
