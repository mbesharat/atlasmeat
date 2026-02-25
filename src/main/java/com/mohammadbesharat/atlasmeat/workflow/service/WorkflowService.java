package com.mohammadbesharat.atlasmeat.workflow.service;


import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

    private final CheckoutService checkoutService;
    private final OrderService orderService;

    public WorkflowService(
            CheckoutService checkoutService,
            OrderService orderService
    ){
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }

}
