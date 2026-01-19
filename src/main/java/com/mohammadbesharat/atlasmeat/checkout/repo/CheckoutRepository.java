package com.mohammadbesharat.atlasmeat.checkout.repo;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CheckoutRepository extends JpaRepository<Checkout, Long>, JpaSpecificationExecutor<Checkout>{
    
}
