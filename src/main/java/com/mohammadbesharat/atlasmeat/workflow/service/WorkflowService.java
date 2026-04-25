package com.mohammadbesharat.atlasmeat.workflow.service;


import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.service.OrderService;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class WorkflowService {

    private final CheckoutService checkoutService;
    private final OrderService orderService;

    public WorkflowService(CheckoutService checkoutService, OrderService orderService){
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }

    private CheckoutResponse toCheckoutResponse(Checkout checkout){
        List<OrderResponse> orderDtos = checkout.getOrders().stream().map(order -> {
                List<OrderItemResponse> itemDtos = order.getItems().stream()
                        .sorted(Comparator.comparing(OrderItem::getId)).map(orderService::toItemDto).toList();

                return new OrderResponse(
                        order.getId(),
                        order.getAnimalType(),
                        itemDtos
                );

        })
        .toList();
        return new CheckoutResponse(
                checkout.getId(),
                checkout.getCustomerName(),
                checkout.getCustomerPhone(),
                checkout.getCustomerEmail(),
                checkout.getStatus(),
                checkout.getCreatedAt(),
                orderDtos
        );
    }

    @Transactional
    public CheckoutResponse addOrderToCheckout(Long checkoutId, CreateOrderRequest orderRequest){

        Checkout checkout = checkoutService.getCheckoutById(checkoutId);
        checkoutService.assertUnlocked(checkout, "add orders");

        Order order = new Order();
        order.setAnimal(orderRequest.animal());

        checkout.addOrder(order);

        Map<Long, Integer> cutQty = orderService.mergeCutQuantities(orderRequest.items());

        orderService.addItemsToOrder(order, cutQty);
        return toCheckoutResponse(checkoutService.saveCheckout(checkout));
    }
}

