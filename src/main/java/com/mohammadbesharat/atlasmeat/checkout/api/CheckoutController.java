package com.mohammadbesharat.atlasmeat.checkout.api;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.dto.UpdateCheckoutStatusRequest;
import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;

import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {
    
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }

    //create a checkout
    @PostMapping
    public ResponseEntity<CheckoutResponse> createCheckout(@Valid @RequestBody CreateCheckoutRequest req){
        CheckoutResponse created = checkoutService.createCheckout(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //get a checkout using id
    @GetMapping("/{checkoutId}")
    public ResponseEntity<CheckoutResponse> getCheckout(@PathVariable Long checkoutId){
        CheckoutResponse response = checkoutService.getCheckout(checkoutId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<CheckoutResponse> getCheckouts(
        @RequestParam(required = false) Long checkoutId,
        @RequestParam(required = false) CheckoutStatus status,
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String customerPhone,
        @RequestParam(required = false) String customerEmail,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
    {
        return checkoutService.searchCheckouts(checkoutId, status, customerName, customerPhone, customerEmail, from, to, pageable);
    }


    //add an order to a checkout
    @PostMapping("/{checkoutId}/orders")
    public ResponseEntity<CheckoutResponse> addOrderToCheckout(@PathVariable Long checkoutId, @Valid @RequestBody CreateOrderRequest request){
        CheckoutResponse updated = checkoutService.addOrderToCheckout(checkoutId, request);
        return ResponseEntity.ok(updated);

    }

    //Remove order from a checkout
    @DeleteMapping("/{checkoutId}/orders/{orderId}")
    public ResponseEntity<Void> removeOrder(@PathVariable Long checkoutId, @PathVariable Long orderId){
        checkoutService.removeOrderFromCheckout(checkoutId, orderId);
        return ResponseEntity.noContent().build();

    }

    //update checkout status
    @PatchMapping ("/{checkoutId}/status")
    public ResponseEntity<CheckoutResponse> updateStatus(@PathVariable Long checkoutId, @Valid @RequestBody  UpdateCheckoutStatusRequest request){
        CheckoutResponse updated = checkoutService.updateCheckoutStatus(checkoutId, request.status());
        return ResponseEntity.ok(updated);
    }


        
        
    

    

}

