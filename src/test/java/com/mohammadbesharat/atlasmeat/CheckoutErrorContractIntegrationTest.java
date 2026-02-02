package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CheckoutErrorContractIntegrationTest extends IntegrationTestBase{

    long ribeyeId;

    @BeforeEach
    void seedCuts(){
        clearDB();
        ribeyeId = seedBeefCut("RIBEYE", "Ribeye");
    }

    @Test
    void return400ErrorShape_whenMissingEmail() throws Exception{

        postJson("/checkouts", 
            TestFixtures.createCheckoutMissingEmail())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status")
                .value(400))
            .andExpect(jsonPath("$.error")
                .value("Bad Request"))
            .andExpect(jsonPath("$.message")
                .value("Validation failed"))
            .andExpect(jsonPath("$.validationErrors").isArray());

    }

    @Test 
    void return404ErrorShape_whenCheckoutNotFound() throws Exception{
        getJson("/checkouts/{checkoutId}", 99999)
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status")
                .value(404))
            .andExpect(jsonPath("$.error")
                .value("Not Found"))
            .andExpect(jsonPath("$.message")
                .value("Checkout not found with id " + 99999))
            .andExpect(jsonPath("$.validationErrors").isArray());
    }

    @Test 
    void return409ErrorShape_whenCheckoutLocked() throws Exception{
        long checkoutId = createCheckoutAndGetId();
        OrderIds ids = addBeefOrderAndGetIds(checkoutId, ribeyeId, 5);

        submitCheckout(checkoutId);

        patchJson("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}",
            TestFixtures.patchItemQuantity(10), 
            checkoutId, ids.orderId(), ids.orderItemId())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.status")
                .value(409))
            .andExpect(jsonPath("$.error")
                .value("Conflict"))
            .andExpect(jsonPath("$.message")
                .value("Cannot edit items when checkout status is SUBMITTED"))
            .andExpect(jsonPath("$.validationErrors").isArray());
        
    }
}

