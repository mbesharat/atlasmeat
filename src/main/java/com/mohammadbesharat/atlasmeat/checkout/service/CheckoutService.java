package com.mohammadbesharat.atlasmeat.checkout.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutAnimalMismatch;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidDateRange;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutRepository;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutSpecifications;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.dto.OrderItemResponse;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import com.mohammadbesharat.atlasmeat.order.dto.OrderResponse;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {
 
    
    public final CheckoutRepository checkoutRepository;
    public final OrderRepository orderRepository;
    public final CutRepository cutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository, CutRepository cutRepository, OrderRepository orderRepository){
        this.checkoutRepository = checkoutRepository;
        this.cutRepository = cutRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public CheckoutResponse createCheckout(CreateCheckoutRequest req){
        Checkout checkout = new Checkout();
        checkout.setCustomerName(req.customerName());
        checkout.setCustomerPhone(req.customerPhone());
        checkout.setCustomerEmail(req.customerEmail());
        checkout.setStatus(CheckoutStatus.SUBMITTED);


        for (CreateOrderRequest orderReq : req.orders()){
            Order order = new Order();
            order.setAnimal(orderReq.animal());
            checkout.AddOrder(order);
            
            Map<Long, Integer> cutQty = mergeCutQuantities(orderReq.items());
            for (Map.Entry<Long, Integer> entry : cutQty.entrySet()){
                Long cutId = entry.getKey();
                Integer quantity = entry.getValue();

                Cut cut = cutRepository.findById(cutId).orElseThrow(()-> new CutNotFound("Cut not found " + cutId));

                if(cut.getAnimalType() != order.getAnimalType()){
                    throw new CutAnimalMismatch("Cut " + cutId + " (" + cut.getDisplayName() + ") is not valid for animal type " + order.getAnimalType());
                }
                
                OrderItem item = new OrderItem();
                item.setCut(cut);
                item.setQuantity(quantity);

                order.addItem(item);
            }

        }

        checkoutRepository.save(checkout);
        return toCheckoutResponse(checkout);
    }




    public OrderItemResponse toItemDto(OrderItem item){
        return new OrderItemResponse(
            item.getCut().getId(),
            item.getCut().getDisplayName(),
            item.getQuantity()
        );
    }






    private CheckoutResponse toCheckoutResponse(Checkout checkout){

        List<OrderResponse> orderDtos = checkout.getOrders().stream().map(order -> {List<OrderItemResponse> itemDtos = order.getItems().stream().map(this::toItemDto).toList();
            return new OrderResponse(
                order.getId(),
                order.getAnimalType(),
                itemDtos
            );
         })
        .toList();

        return new CheckoutResponse(
            checkout.getId(),
            checkout.getCustomerName(),
            checkout.getCustomerPhone(),
            checkout.getCustomerEmail(),
            checkout.getStatus(),
            checkout.getCreatedAt(),
            orderDtos
        );
    }





    public CheckoutResponse getCheckout(Long checkoutId){
        
        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow(() -> new CheckoutNotFound("Checkout not found with id " + checkoutId));
        return toCheckoutResponse(checkout);
    }






    private Map<Long, Integer> mergeCutQuantities(List<CreateOrderItemRequest> items){
        Map<Long, Integer> result = new LinkedHashMap<>();

        for(CreateOrderItemRequest item : items){
            Long cutId = item.cutId();
            Integer qty = item.quantity();
            result.merge(cutId, qty, Integer::sum);
        }
        return result;
    }






    public Page<CheckoutResponse> getAllCheckouts(Pageable pageable) {
        
        Page<Checkout> checkouts = checkoutRepository.findAll(pageable);
        return checkouts.map(this::toCheckoutResponse);
    }






    public CheckoutResponse addOrderToCheckout(Long checkoutId, Long orderId){

        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow(() -> new CheckoutNotFound("Checkout not found with id " + checkoutId));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));

        if(checkout.getStatus() != CheckoutStatus.DRAFT){
            throw new IllegalStateException("Cannot add orders to submitted checkout");
        }

        boolean alreadyInCheckout = checkout.getOrders().stream().anyMatch(o -> o.getId().equals(orderId));

        if(alreadyInCheckout){
            throw new IllegalStateException("Order " + orderId + "is already in checkout " + checkoutId);
        }
        checkout.getOrders().add(order);
        order.setCheckout(checkout);
        Checkout saved = checkoutRepository.save(checkout);
        return toCheckoutResponse(saved);
    }


    public Page<CheckoutResponse> searchCheckouts(
        CheckoutStatus status,
        String customerEmail,
        LocalDate from,
        LocalDate to,
        Pageable pageable
    ){
        if (from != null && to != null && from.isAfter(to)){
            throw new InvalidDateRange("From must be on or before to");
        }
        LocalDateTime start = null;
        LocalDateTime endExclusive = null;
        if (from != null) start = from.atStartOfDay();
        if (to != null) endExclusive = to.plusDays(1).atStartOfDay();

        Specification<Checkout> spec = (root, query, cb) -> cb.conjunction();
        if (status != null){
            spec = spec.and(CheckoutSpecifications.hasStatus(status));
        }
        if (customerEmail != null){
            spec = spec.and(CheckoutSpecifications.customerEmailContains(customerEmail));
        }
        if (start != null){
            spec = spec.and(CheckoutSpecifications.createdAtGte(start));
        }
        if (endExclusive != null){
            spec = spec.and(CheckoutSpecifications.createdAtLt(endExclusive));
        }

        Page<Checkout> page = checkoutRepository.findAll(spec, pageable);

        return page.map(this::toCheckoutResponse);
    }
}
