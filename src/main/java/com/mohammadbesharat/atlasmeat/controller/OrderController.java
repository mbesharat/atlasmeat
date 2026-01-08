package com.mohammadbesharat.atlasmeat.controller;

import com.mohammadbesharat.atlasmeat.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.model.Order;
import com.mohammadbesharat.atlasmeat.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody CreateOrderRequest req){

        return orderService.createOrder(req);
    }
}
