package com.mohammadbesharat.atlasmeat.order.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.domain.Order;

;

public interface CutRepository extends JpaRepository<Cut, Long>{
    
    Optional<Order> findByIdAndOrderId(Long id, Long orderId);

}
