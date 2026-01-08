package com.mohammadbesharat.atlasmeat.service;


import com.mohammadbesharat.atlasmeat.dto.CreateOrderRequest;
// import com.mohammadbesharat.atlasmeat.future.availability.OrderStatus;
// import com.mohammadbesharat.atlasmeat.future.availability.DayLimit;
// import com.mohammadbesharat.atlasmeat.future.availability.DayLimitRepository;
// import com.mohammadbesharat.atlasmeat.future.deposit.DepositStatus;
import com.mohammadbesharat.atlasmeat.model.Order;
import com.mohammadbesharat.atlasmeat.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrderService {
    
    public final OrderRepository orderRepository;
    // public final DayLimitRepository dayLimitRepository;

    //this initializes the values of order and dayLimit rep
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
        // this.dayLimitRepository = dayLimitRepository;
    }

    
    public Order createOrder(CreateOrderRequest req){

        //this checks for required values in the req object
        // if (req == null || req.scheduledDate == null){
        //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scheduled date is required");
        // }
        if (req.customerName == null || req.customerName.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer name is required");
        }
        if (req.customerEmail == null || req.customerEmail.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer email is required"); 
        }
        if (req.customerPhone == null || req.customerPhone.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer phone is required");
        }
        if (req.orderDetails == null || req.orderDetails.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order details are needed");
        }

        //this creates an object that is used to check the limit for the chosen day
        // DayLimit limit = dayLimitRepository.findByDate(req.scheduledDate).orElse(null);

        // if (limit == null){
        //     throw new ResponseStatusException(HttpStatus.CONFLICT, "Day is not configured, max limit reached");
        // }

        // //this checks a day's openness, if openness is null then it infers based on the order limit set
        // boolean open;

        // if (limit.getOpen() != null){

        //     open = limit.getOpen();
        // }
        // else{
        //     open = limit.getMaxOrders() != null && limit.getMaxOrders() > 0;
        // }

        // //this sets the value of the object maxOrders based on if limit.maxOrders is null, if not it is its value if null it is 0
        // //nullness of maxOrder wont cause any issues
        // int maxOrders = limit.getMaxOrders() != null ? limit.getMaxOrders() : 0;

        // //if the day is not available an error is thrown
        // if(!open || maxOrders <= 0){
        //     throw new ResponseStatusException(HttpStatus.CONFLICT, "Day is closed");
        // }

        // //this sets an object to hold the amount of bookings for a day and another that holds that amount remaining for the day
        // long booked = orderRepository.countByScheduledDateAndStatusNot(req.scheduledDate, OrderStatus.Cancelled);
        // long remaining = Math.max(0, maxOrders - booked);

        // if (remaining <= 0){
        //     throw new ResponseStatusException(HttpStatus.CONFLICT, "No slots remaining for the day");
        // }

        //create order object and set values
        Order order = new Order();
        // order.setScheduledDate(req.scheduledDate);
        order.setCustomerEmail(req.customerEmail.trim());
        order.setCustomerName(req.customerName.trim());
        order.setCustomerPhone(req.customerPhone.trim());
        order.setOrderDetails(req.orderDetails);
        // order.setStatus(OrderStatus.PendingPayment);
        // order.setDepositCents(0);
        // order.setDepositStatus(DepositStatus.unpaid);

        //this has the order saved into the DB
        return orderRepository.save(order);

    }
}
