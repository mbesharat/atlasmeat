package com.mohammadbesharat.atlasmeat.order.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.Cut;

;

public interface CutRepository extends JpaRepository<Cut, Long>{
    
    Optional<Cut> findByIdAndOrderId(Long id, Long orderId);

}
