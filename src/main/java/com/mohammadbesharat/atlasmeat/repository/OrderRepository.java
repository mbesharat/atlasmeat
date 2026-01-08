package com.mohammadbesharat.atlasmeat.repository;

// import com.mohammadbesharat.atlasmeat.future.availability.OrderStatus;
import com.mohammadbesharat.atlasmeat.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
    //this counts all dates that have status as !cancelled
    long countByScheduledDateAndStatusNot(LocalDate scheduledDate);

    //this fetches scheduled dates in a range
    List<Order> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
}
