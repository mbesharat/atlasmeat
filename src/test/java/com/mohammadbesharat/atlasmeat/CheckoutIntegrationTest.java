package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderItemRepository;

class CheckoutIntegrationTest extends IntegrationTestBase{

    @Autowired CutRepository cutRepository;
    @Autowired OrderItemRepository orderItemRepository;

    private long ribeyeId;

    @BeforeEach
    void seedCuts(){
        cutRepository.deleteAll();

        Cut ribeye = new Cut();
        ribeye.setAnimal(AnimalType.BEEF);
        ribeye.setCode("RIBEYE");
        ribeye.setDisplayName("Ribeye");
        ribeyeId = cutRepository.save(ribeye).getId();

        Cut brisket = new Cut();
        brisket.setAnimal(AnimalType.BEEF);
        brisket.setCode("BRISKET");
        brisket.setDisplayName("Brisket");
        cutRepository.save(brisket);
    }

    @Test
    void fullLifecycle_createCheckout_addOrder_patchItem_deleteItem() throws Exception{

        //POST /checkouts
        long checkoutId = createCheckoutAndGetId();



        //POST /checkouts/{checkoutId}/orders
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 2);
        long orderId = ids.orderId();
        long orderItemId = ids.orderItemId();


        //Patch item quantity
        patchJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
            TestFixtures.patchItemQuantity(5), checkoutId, orderId, orderItemId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders[0].items[0].quantity").value(5));

        //DELETE item
        deleteJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
            checkoutId, orderId, orderItemId)
            .andExpect(status().isNoContent());

        //GET checkout and assert items empty
        getCheckout(checkoutId)
            .andExpect(jsonPath("$.orders[0].items").isArray())
            .andExpect(jsonPath("$.orders[0].items.length()").value(0));
    }
}