package com.mohammadbesharat.atlasmeat.checkout.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;
import com.mohammadbesharat.atlasmeat.checkout.dto.CreateCheckoutRequest;
import com.mohammadbesharat.atlasmeat.checkout.repo.CheckoutRepository;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.domain.Order;
import com.mohammadbesharat.atlasmeat.order.domain.OrderItem;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderRequest;
import com.mohammadbesharat.atlasmeat.order.dto.CreateOrderItemRequest;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {
 
    
    public final CheckoutRepository checkoutRepository;
    public final CutRepository cutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository, CutRepository cutRepository){
        this.checkoutRepository = checkoutRepository;
        this.cutRepository = cutRepository;
    }

    @Transactional
    public Checkout createCheckout(CreateCheckoutRequest req){
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

                Cut cut = cutRepository.findById(cutId).orElseThrow(()-> new NoSuchElementException("Cut not found " + cutId));

                if(cut.getAnimalType() != order.getAnimalType()){
                    throw new IllegalArgumentException("Cut " + cutId + " (" + cut.getDisplayName() + ") is not valid for animal type " + order.getAnimalType());
                }
                
                OrderItem item = new OrderItem();
                item.setCut(cut);
                item.setQuantity(quantity);

                order.addItem(item);
            }

        }

        return checkoutRepository.save(checkout);
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


}
