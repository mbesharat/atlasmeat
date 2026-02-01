package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CheckoutErrorContractIntegrationTest extends IntegrationTestBase{

    @Test
    void badRequestContract() throws Exception{

        postJson("/checkouts", 
            TestFixtures.createCheckoutMissingEmail())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error")
                .value("Bad Request"))
            .andExpect(jsonPath("$.message")
                .value("Validation failed"))
            .andExpect(jsonPath("$.validationErrors").isArray());

    }

    
}
