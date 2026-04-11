package com.mohammadbesharat.atlasmeat.workflow.service;


import com.mohammadbesharat.atlasmeat.checkout.dto.CheckoutResponse;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.service.CheckoutService;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

    private final CheckoutService checkoutService;

    public WorkflowService(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }

    public CheckoutResponse startCheckoutWorkflow(CreateCheckoutRequest request){
        return checkoutService.createCheckout(request);
    }


}
