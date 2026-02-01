package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutIntegrationTest extends IntegrationTestBase{

    private long ribeyeId;

    @BeforeEach
    void seedCuts(){
        clearDB();
        ribeyeId = seedBeefCut("RIBEYE", "Ribeye");
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