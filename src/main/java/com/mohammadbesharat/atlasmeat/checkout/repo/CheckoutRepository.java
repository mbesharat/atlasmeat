package com.mohammadbesharat.atlasmeat.checkout.repo;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long>{
    
}
