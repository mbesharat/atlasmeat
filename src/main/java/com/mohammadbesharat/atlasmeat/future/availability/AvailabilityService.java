// package com.mohammadbesharat.atlasmeat.future.availability;

// import com.mohammadbesharat.atlasmeat.repository.OrderRepository;

// import org.springframework.stereotype.Service;
// import java.time.LocalDate;
// import java.util.*;


// @Service
// public class AvailabilityService {
    
//     private final DayLimitRepository dayLimitRepository;
//     private final OrderRepository orderRepository;

//     private static final boolean DEFAULT_OPEN = true;
//     private static final int DEFAULT_MAX_ORDERS = 10;

//     public AvailabilityService(DayLimitRepository dayLimitRepository, OrderRepository orderRepository){
//         this.dayLimitRepository = dayLimitRepository;
//         this.orderRepository = orderRepository;
//     }

//     public long countBookedForDate(LocalDate date){
//             return orderRepository.countByScheduledDateAndStatusNot(date, OrderStatus.Cancelled);
//         }

//     public List<AvailabilityDay> getAvailability(LocalDate from, LocalDate to){
//         if(to.isBefore(from)){
//             throw new IllegalArgumentException("'to' must be on or after 'from'");
//         }
    

//         List<DayLimit> limits = dayLimitRepository.findByDateBetween(from, to);

//         Map<LocalDate, DayLimit> limitByDate = new HashMap<>();

//         for(DayLimit d1 : limits){
//             limitByDate.put(d1.getDate(), d1);
//         }

//         List<AvailabilityDay> result = new ArrayList<>();
//         LocalDate d = from;

        

//         while(!d.isAfter(to)){
//             DayLimit limit = limitByDate.get(d);

//             boolean open = DEFAULT_OPEN;
//             int maxOrders = DEFAULT_MAX_ORDERS;

//             if(limit != null){

//                 if(limit.getOpen() != null){
//                 open = limit.getOpen();
//             }
//             else{
//                 open = limit.getMaxOrders() != null && limit.getMaxOrders() > 0;
//             }

//                 maxOrders = (limit.getMaxOrders() != null) ? limit.getMaxOrders() : 0;
//             }

//             long booked = 0;

//             if(open && maxOrders > 0){
//                 booked = countBookedForDate(d);
//             }

//             result.add(new AvailabilityDay(d, open, maxOrders, booked));

//             d = d.plusDays(1);
//         }

//         return result;
//     }
// }
