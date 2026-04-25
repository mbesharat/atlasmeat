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
import com.mohammadbesharat.atlasmeat.checkout.dto.UpdateItemRequest;
import com.mohammadbesharat.atlasmeat.checkout.dto.UpdateOrderRequest;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutLockedException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutAnimalMismatch;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidDateRange;
import com.mohammadbesharat.atlasmeat.common.exception.InvalidPatchRequest;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidStatusTransition;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.OrderItemNotFound;
import com.mohammadbesharat.atlasmeat.common.exception.OrderNotInCheckout;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutRepository;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutSpecifications;
import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderItemRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {
 
    
    private final CheckoutRepository checkoutRepository;
    private final OrderRepository orderRepository;
    private final CutRepository cutRepository;
    private final OrderItemRepository orderItemRepository;

    public CheckoutService(
            CheckoutRepository checkoutRepository,
            CutRepository cutRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository){
        this.checkoutRepository = checkoutRepository;
        this.cutRepository = cutRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public CheckoutResponse createCheckout(CreateCheckoutRequest req){
        Checkout checkout = new Checkout();
        checkout.setCustomerName(req.customerName());
        checkout.setCustomerPhone(req.customerPhone());
        checkout.setCustomerEmail(req.customerEmail());
        checkout.setStatus(CheckoutStatus.DRAFT);

        checkoutRepository.save(checkout);
        return toCheckoutResponse(checkout);
    }

    public Checkout saveCheckout(Checkout checkout){

        return checkoutRepository.save(checkout);
    }









    public CheckoutResponse getCheckout(Long checkoutId){
        Checkout checkout = checkoutRepository.findByIdWithOrdersItemsAndCut(checkoutId).orElseThrow(()
                -> new CheckoutNotFound(checkoutId));
        return toCheckoutResponse(checkout);
    }

    public Checkout getCheckoutById(Long checkoutId){
        return checkoutRepository.findById(checkoutId).orElseThrow(() ->
                new CheckoutNotFound(checkoutId));
    }


    public void assertUnlocked(Checkout checkout, String action){
        if(checkout.getStatus() != CheckoutStatus.DRAFT){
            throw new CheckoutLockedException(action,  checkout.getStatus());
        }
    }










    public Page<CheckoutResponse> getAllCheckouts(Pageable pageable) {
        
        Page<Checkout> checkouts = checkoutRepository.findAll(pageable);
        return checkouts.map(this::toCheckoutResponse);
    }

    @Transactional
    public void removeOrderFromCheckout(Checkout checkout, Order order){
        checkout.removeOrder(order);
        saveCheckout(checkout);
    }
















    @Transactional 
    public CheckoutResponse patchItem(Long checkoutId, Long orderId, Long orderItemId, UpdateItemRequest request){

        Checkout checkout = checkoutRepository.findByIdWithOrdersItemsAndCut(checkoutId).orElseThrow(() ->
                new CheckoutNotFound(checkoutId));

        if(checkout.getStatus() != CheckoutStatus.DRAFT){
            throw new CheckoutLockedException("edit items", checkout.getStatus());
        }
        orderRepository.findByIdAndCheckoutId(orderId, checkoutId).orElseThrow(() ->
                new OrderNotInCheckout(orderId, checkoutId));
        if(request.quantity() == null || request.quantity() < 1){
            throw new InvalidPatchRequest("Quantity must be 1 or greater");
        }

        OrderItem item = orderItemRepository.findByIdAndOrderIdAndOrderCheckoutId(
                orderItemId, orderId, checkoutId).orElseThrow(() ->
                new OrderItemNotFound(orderItemId, orderId, checkoutId));

        item.setQuantity(request.quantity());
        orderItemRepository.save(item);
        return toCheckoutResponse(checkout);

    }






    @Transactional
    public void removeItemFromOrder(Long checkoutId, Long orderId, Long orderItemId){

        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow(() -> new CheckoutNotFound(checkoutId));

        if(checkout.getStatus() != CheckoutStatus.DRAFT){
            throw new CheckoutLockedException("remove items", checkout.getStatus());
        }

        OrderItem item = orderItemRepository.findByIdAndOrderIdAndOrderCheckoutId(orderItemId, orderId, checkoutId).orElseThrow(() -> new OrderItemNotFound(orderItemId, orderId, checkoutId));

        orderItemRepository.delete(item);




    }


    public Page<CheckoutResponse> searchCheckouts(
        Long checkoutId,
        CheckoutStatus status,
        String customerName,
        String customerPhone,
        String customerEmail,
        LocalDate from,
        LocalDate to,
        Pageable pageable
    ){
        if (from != null && to != null && from.isAfter(to)){
            throw new InvalidDateRange();
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
        if (customerName != null){
            spec = spec.and(CheckoutSpecifications.customerNameContains(customerName));
        }
        if (customerPhone != null){
            spec = spec.and(CheckoutSpecifications.customerPhoneContains(customerPhone));
        }

        Page<Checkout> page = checkoutRepository.findAll(spec, pageable);

        return page.map(this::toCheckoutResponse);
    }

    

    @Transactional
    public CheckoutResponse updateCheckoutStatus(Long checkoutId, CheckoutStatus newStatus){
        
        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow(() -> new CheckoutNotFound(checkoutId));
        CheckoutStatus currentStatus = checkout.getStatus();
        if(!isAllowedTransition(currentStatus, newStatus)){
            throw new InvalidStatusTransition(currentStatus, newStatus);
        }

        checkout.setStatus(newStatus);
        Checkout saved = checkoutRepository.save(checkout);
        return toCheckoutResponse(saved);

    }

    private boolean isAllowedTransition(CheckoutStatus current, CheckoutStatus next){
        return switch (current){
            case DRAFT -> next == CheckoutStatus.SUBMITTED || next == CheckoutStatus.CANCELLED;
            case SUBMITTED -> next == CheckoutStatus.PAID || next == CheckoutStatus.CANCELLED;
            case PAID, CANCELLED -> false;
        };
    }
}
