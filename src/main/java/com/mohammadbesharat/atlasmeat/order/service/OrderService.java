package com.mohammadbesharat.atlasmeat.order.service;


import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import static com.mohammadbesharat.atlasmeat.order.exceptions.OrderExceptions.*;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;

import org.springframework.stereotype.Service;


@Service
public class OrderService {
    
    public final OrderRepository orderRepository;
    
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
        
    }
    
    public Order createOrder(CreateOrderRequest req){

        // validation checks
        if (req.customerName == null || req.customerName.trim().isEmpty()){
            throw missingName();
        }
        if (req.customerEmail == null || req.customerEmail.trim().isEmpty()){
            throw missingEmail(); 
        }
        if (req.customerPhone == null || req.customerPhone.trim().isEmpty()){
            throw missingPhone();
        }
        if (req.orderDetails == null || req.orderDetails.trim().isEmpty()){
            throw missingDetails();
        }

        //create order object and set values
        Order order = new Order();
        order.setCustomerEmail(req.customerEmail.trim());
        order.setCustomerName(req.customerName.trim());
        order.setCustomerPhone(req.customerPhone.trim());
        order.setOrderDetails(req.orderDetails);
        

        //this has the order saved into the DB
        return orderRepository.save(order);

    }

    public Order findOrderById(Long id){

        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
