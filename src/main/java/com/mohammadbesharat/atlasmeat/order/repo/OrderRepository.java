package com.mohammadbesharat.atlasmeat.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.Order;

;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
