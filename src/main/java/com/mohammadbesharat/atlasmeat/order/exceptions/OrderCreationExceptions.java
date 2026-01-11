package com.mohammadbesharat.atlasmeat.order.exceptions;

public final class OrderCreationExceptions {
    
    private OrderCreationExceptions(){};

    public static BadRequestException missingName(){

        return new BadRequestException("Customer name is required");
    }

    public static BadRequestException missingEmail(){

        return new BadRequestException("Customer email is required");
    }

    public static BadRequestException missingPhone(){

        return new BadRequestException("Customer phone number is needed");
    }

    public static BadRequestException missingDetails(){

        return new BadRequestException("Order details are needed");
    }
}
