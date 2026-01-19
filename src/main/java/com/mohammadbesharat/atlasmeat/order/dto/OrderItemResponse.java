package com.mohammadbesharat.atlasmeat.order.dto;

public record OrderItemResponse(

    Long cutId,
    String cutName,
    Integer quantity
){}
