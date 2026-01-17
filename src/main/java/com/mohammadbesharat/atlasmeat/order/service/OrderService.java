package com.mohammadbesharat.atlasmeat.order.service;


import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class OrderService {
    
    public final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    
    public OrderResponse createOrder(CreateOrderRequest req){

        Order saved = orderRepository.save(toEntity(req));
        return toDto(saved);
    }

    private Order toEntity(CreateOrderRequest req){
        Order order = new Order();
        order.setAnimal(req.animal());
        return order;

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
        return new OrderResponse(
            o.getId(),
            o.getCustomerName(),
            o.getCustomerEmail(),
            o.getCustomerPhone(),
            o.getCreatedAt(),
            o.getCancelledAt());
        
    }
}
