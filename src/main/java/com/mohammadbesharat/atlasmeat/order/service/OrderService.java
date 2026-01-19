package com.mohammadbesharat.atlasmeat.order.service;


import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class OrderService {
    
    public final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order findOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderResponse getOrderById(Long id){
        return toDto(findOrderById(id));
    }

    public Page<OrderResponse> getOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(this::toDto);
    }

    private OrderResponse toDto(Order o){
        List<OrderItemResponse> itemDtos = o.getItems().stream()
            .map(item -> new OrderItemResponse(
                    item.getCut().getId(),
                    item.getCut().getDisplayName(),
                    item.getQuantity()
            ))
            .toList();
        return new OrderResponse(
            o.getId(),
            o.getAnimalType(),
            itemDtos
        );
    }
}
