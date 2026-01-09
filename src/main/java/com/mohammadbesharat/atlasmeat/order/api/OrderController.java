package com.mohammadbesharat.atlasmeat.order.api;

import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping
    public Order create(@RequestBody CreateOrderRequest req){

        return orderService.createOrder(req);
    }
    //display order by number to user
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.findOrderById(id);
    }

    @GetMapping("/orders")
    public Page<Order> getOrders(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return orderService.getOrders(pageable);
    }
    
    
}
