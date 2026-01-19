package com.mohammadbesharat.atlasmeat.checkout.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {
    
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> createCheckout(@Valid @RequestBody CreateCheckoutRequest req){
        CheckoutResponse created = checkoutService.createCheckout(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{checkoutId}")
    public ResponseEntity<CheckoutResponse> getCheckout(@PathVariable Long checkoutId){
        CheckoutResponse response = checkoutService.getCheckout(checkoutId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CheckoutResponse>> getAllCheckouts(){
        List<CheckoutResponse> response = checkoutService.getAllCheckouts();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{checkoutId}/orders/{orderId}")
    public ResponseEntity<CheckoutResponse> addOrderToCheckout(@PathVariable Long checkoutId, @PathVariable Long orderId){
        CheckoutResponse updated = checkoutService.addOrderToCheckout(checkoutId, orderId);
        return ResponseEntity.ok(updated);

    }

    

}

