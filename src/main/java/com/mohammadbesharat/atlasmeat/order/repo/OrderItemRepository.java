package com.mohammadbesharat.atlasmeat.order.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;


;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
    Optional<OrderItem> findByOrderIdAndCheckoutIdAndCutId(Long orderId, Long checkoutId, Long cutId);

}
