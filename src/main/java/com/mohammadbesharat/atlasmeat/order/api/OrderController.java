package com.mohammadbesharat.atlasmeat.order.api;

import com.mohammadbesharat.atlasmeat.order.dto.*;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;




@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    //take the values from user
    //display order by number to user
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public Page<OrderResponse> getOrders(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return orderService.getOrders(pageable);
    }
    
    
}
