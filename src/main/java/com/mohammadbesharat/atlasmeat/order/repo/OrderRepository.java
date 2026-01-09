package com.mohammadbesharat.atlasmeat.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammadbesharat.atlasmeat.order.domain.Order;

import java.util.List;
import java.time.LocalDate;
;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
    //this counts all dates that have status as !cancelled
    long countByScheduledDate(LocalDate scheduledDate);

    //this fetches scheduled dates in a range
    List<Order> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
}
