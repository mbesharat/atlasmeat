package com.mohammadbesharat.atlasmeat.order.dto;

public record OrderItemResponse(

    Long orderItemId,
    Long cutId,
    String cutName,
    Integer quantity
){}
