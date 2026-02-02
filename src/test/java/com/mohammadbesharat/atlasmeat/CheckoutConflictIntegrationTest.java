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

    //Happy cycle for status transitions
    @Test
    void return200WhenLegalStatusTransitions_DRAFT_SUBMITTED_PAID() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 3);

        submitCheckout(checkoutId);
        markAsPaid(checkoutId);
            
    }


    //Invalid status transition tests
    @Test
    void return409WhenDraftToPaid() throws Exception{

        long checkoutId = createCheckoutAndGetId();

        patchJson("/checkouts/{checkoutId}/status", 
            TestFixtures.updateCheckoutPaid(), checkoutId)
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("DRAFT cannot be changed to PAID"));
    }

    @Test
    void return409WhenSubmittedToDraft() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 3);
        submitCheckout(checkoutId);

        patchJson("/checkouts/{checkoutId}/status",
            TestFixtures.updateCheckoutDraft(), checkoutId)
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("SUBMITTED cannot be changed to DRAFT"));
    }

    @Test
    void return409WhenPaidToDraftOrCancelled() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 3);
        submitCheckout(checkoutId);
        markAsPaid(checkoutId);
        
        //PAID to DRAFT
        patchJson("/checkouts/{checkoutId}/status", TestFixtures.updateCheckoutDraft(), checkoutId)
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("PAID cannot be changed to DRAFT"));

        //PAID to CANCELLED
        patchJson("/checkouts/{checkoutId}/status", TestFixtures.updateCheckoutCancelled(), checkoutId)
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("PAID cannot be changed to CANCELLED"));
    }

    //CheckoutLocked conflict tests
    @Test
    void return409EditingItemsWhenSubmitted() throws Exception{

        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 5);
        
        submitCheckout(checkoutId);

        patchJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
            TestFixtures.patchItemQuantity(10),
            checkoutId,
            ids.orderId(),
            ids.orderItemId())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("Cannot edit items when checkout status is SUBMITTED"));

    }
    
}
