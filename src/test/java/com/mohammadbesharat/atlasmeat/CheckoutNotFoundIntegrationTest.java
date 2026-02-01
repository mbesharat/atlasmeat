package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutNotFoundIntegrationTest extends IntegrationTestBase {

    private long ribeyeId;

    @BeforeEach
    void seedCuts(){
        clearDB();
        ribeyeId = seedBeefCut("RIBEYE", "Ribeye");
    }

    @Test
    void return404WhenCheckoutNotFound() throws Exception{
        
        
        getJson("/checkouts/{checkoutId}", 99999)
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Checkout not found with id " + 99999));
    }

    @Test
    void return404WhenAddingOrderToMissingCheckout() throws Exception{

        postJson("/checkouts/{checkoutId}/orders", 
            TestFixtures.addBeefOrder(1, 3), 99999)
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Checkout not found with id " + 99999));
    }

    @Test
    void return404WhenPatchingBadOrderItemId() throws Exception {

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 2);
        long badOrderItemId = ids.orderItemId() + 99999;

        patchJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
            TestFixtures.patchItemQuantity(5), 
            checkoutId, ids.orderId(), badOrderItemId)
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message")
            .value("Item not found with Id " + badOrderItemId 
            + " in order with ID " + ids.orderId() 
            + " in checkout with ID " + checkoutId));
    }
    
}
