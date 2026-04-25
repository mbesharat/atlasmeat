package com.mohammadbesharat.atlasmeat.order.service;


import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class OrderService {
    
    private final OrderRepository orderRepository;

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

    private OrderResponse toDto(Order order){
        List<OrderItemResponse> itemDtos = order.getItems().stream()
                .sorted(Comparator.comparing(OrderItem::getId))
                .map(this::toItemDto).toList();
        return new OrderResponse(
            order.getId(),
            order.getAnimalType(),
            itemDtos
        );
    }


    public OrderItemResponse toItemDto(OrderItem item){
        return new OrderItemResponse(
                item.getId(),
                item.getCut().getId(),
                item.getCut().getDisplayName(),
                item.getQuantity()
        );
    }

    public Map<Long, Integer> mergeCutQuantities(List<CreateOrderItemRequest> items){
        Map<Long, Integer> result = new LinkedHashMap<>();

        for(CreateOrderItemRequest item : items){
            Long cutId = item.cutId();
            Integer qty = item.quantity();
            result.merge(cutId, qty, Integer::sum);
        }
        return result;
    }
}
