package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CheckoutConflictIntegrationTest extends IntegrationTestBase {

    long ribeyeId;

    @BeforeEach
    void seedCuts(){
        clearDB();
        ribeyeId = seedBeefCut("RIBEYE", "Ribeye");
    }


    @Test
    void return409WhenInvalidCheckoutModification() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 5);
        
        patchJson("/checkouts/{checkoutId}/status", 
            TestFixtures.updateCheckoutStatus("SUBMITTED"), 
            checkoutId)
            .andExpect(status().isOk());

        patchJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
            TestFixtures.patchItemQuantity(10),
            checkoutId,
            ids.orderId(),
            ids.orderItemId())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message")
            .value("Cannot edit items when checkout status is SUBMITTED"));

    }

    @Test
    void return409WhenInvalidStatusTransition() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 3);

        patchJson("/checkouts/{checkoutId}/status", 
            TestFixtures.updateCheckoutStatus("SUBMITTED"),
            checkoutId)
            .andExpect(status().isOk());

        patchJson("/checkouts/{checkoutId}/status",
            TestFixtures.updateCheckoutStatus("DRAFT"),
            checkoutId)
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message")
            .value("SUBMITTED cannot be changed to DRAFT"));
        
    
    }
    
}
