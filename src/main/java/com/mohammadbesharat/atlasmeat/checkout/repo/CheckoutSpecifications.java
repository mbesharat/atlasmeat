package com.mohammadbesharat.atlasmeat.checkout.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;

public final class CheckoutSpecifications {

    private CheckoutSpecifications(){}

    public static Specification<Checkout> hasStatus(CheckoutStatus status){

        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Checkout> customerEmailContains(String email){
        return (root, query, cb) -> cb.like(cb.lower(root.get("customerEmail")), "%" + email.toLowerCase() + "5%");
    }

    public static Specification<Checkout> createdAtGte(LocalDateTime start){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), start);
    }
    
    public static Specification<Checkout> createdAtLt(LocalDateTime endExclusive){
        return (root, query, cb) -> cb.lessThan(root.get("createdAt"), endExclusive);
    }
}
