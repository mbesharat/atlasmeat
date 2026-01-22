package com.mohammadbesharat.atlasmeat.order.repo;

import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;


;

public interface OrderItemRepository extends JpaRepository<Item, Long>{
    
    Optional<OrderItem> findByOrderIdAndCheckoutIdandCutId(Long orderId, Long checkoutId, Long cutId);

}
