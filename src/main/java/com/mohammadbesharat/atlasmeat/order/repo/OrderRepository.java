package com.mohammadbesharat.atlasmeat.order.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.Order;

;

public interface OrderRepository extends JpaRepository<Order, Long>{

    Optional<Order> findByIdAndCheckoutId(Long id, Long checkoutId);
    
}
