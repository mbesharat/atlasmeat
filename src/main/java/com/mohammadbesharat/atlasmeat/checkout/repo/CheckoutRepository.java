package com.mohammadbesharat.atlasmeat.checkout.repo;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckoutRepository extends JpaRepository<Checkout, Long>, JpaSpecificationExecutor<Checkout>{

    @Query("""
        Select distinct c
        From Checkout c
        left join fetch c.orders o
        left join fetch o.items i
        left join fetch i.cut
        Where c.id = :checkoutId
    """)
    Optional<Checkout> findByIdWithOrdersItemsAndCut(@Param("checkoutId") Long checkoutId);
    
}
