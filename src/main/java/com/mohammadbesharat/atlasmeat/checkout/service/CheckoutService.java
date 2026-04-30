package com.mohammadbesharat.atlasmeat.checkout.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutLockedException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutNotFoundException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidDateRangeException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidCheckoutStatusTransitionException;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutRepository;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutSpecifications;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CheckoutService {
 
    
    private final CheckoutRepository checkoutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository){
        this.checkoutRepository = checkoutRepository;
    }

    @Transactional
    public Checkout createCheckout(CreateCheckoutRequest req){
        Checkout checkout = new Checkout();
        checkout.setCustomerName(req.customerName());
        checkout.setCustomerPhone(req.customerPhone());
        checkout.setCustomerEmail(req.customerEmail());
        checkout.setStatus(CheckoutStatus.DRAFT);

        return checkoutRepository.save(checkout);
    }

    public Checkout saveCheckout(Checkout checkout){

        return checkoutRepository.save(checkout);
    }

    public Checkout getCheckout(Long checkoutId){
        return checkoutRepository.findByIdWithOrdersItemsAndCut(checkoutId).orElseThrow(()
                -> new CheckoutNotFoundException(checkoutId));
    }

    public Checkout getCheckoutById(Long checkoutId){
        return checkoutRepository.findById(checkoutId).orElseThrow(() ->
                new CheckoutNotFoundException(checkoutId));
    }

    public void assertUnlocked(Checkout checkout, String action){
        if(checkout.getStatus() != CheckoutStatus.DRAFT){
            throw new CheckoutLockedException(action,  checkout.getStatus());
        }
    }

    @Transactional
    public void removeOrderFromCheckout(Checkout checkout, Order order){
        checkout.removeOrder(order);
        saveCheckout(checkout);
    }

    public Page<Checkout> searchCheckouts(
        CheckoutStatus status,
        String customerName,
        String customerPhone,
        String customerEmail,
        LocalDate from,
        LocalDate to,
        Pageable pageable
    ){
        if (from != null && to != null && from.isAfter(to)){
            throw new InvalidDateRangeException();
        }
        LocalDateTime start = null;
        LocalDateTime endExclusive = null;
        if (from != null) start = from.atStartOfDay();
        if (to != null) endExclusive = to.plusDays(1).atStartOfDay();

        Specification<Checkout> spec = (root, query, cb)
                -> cb.conjunction();
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

        return checkoutRepository.findAll(spec, pageable);
    }

    @Transactional
    public Checkout updateCheckoutStatus(Long checkoutId, CheckoutStatus newStatus){
        
        Checkout checkout = getCheckoutById(checkoutId);
        CheckoutStatus currentStatus = checkout.getStatus();
        if(!isAllowedTransition(currentStatus, newStatus)){
            throw new InvalidCheckoutStatusTransitionException(currentStatus, newStatus);
        }

        checkout.setStatus(newStatus);
        return checkoutRepository.save(checkout);
    }

    private boolean isAllowedTransition(CheckoutStatus current, CheckoutStatus next){
        return switch (current){
            case DRAFT -> next == CheckoutStatus.SUBMITTED || next == CheckoutStatus.CANCELLED;
            case SUBMITTED -> next == CheckoutStatus.PAID || next == CheckoutStatus.CANCELLED;
            case PAID, CANCELLED -> false;
        };
    }
}
