package com.mohammadbesharat.atlasmeat.order.api;

import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    //take the values from user
    @PostMapping
    public Order create(@RequestBody CreateOrderRequest req){

        return orderService.createOrder(req);
    }
    //display order by number to user
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.findOrderById(id);
    }
    
}
