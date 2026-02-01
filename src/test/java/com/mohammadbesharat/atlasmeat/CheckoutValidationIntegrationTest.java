package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutValidationIntegrationTest extends IntegrationTestBase{

    
    @Test
    void return400WhenMissingEmail() throws Exception{

        //POST /checkouts with invalid email
        postJson("/checkouts", TestFixtures.createCheckoutMissingEmail())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith("application/json"))
            .andExpect(jsonPath("$.validationErrors").isArray())
            .andExpect(jsonPath("$.validationErrors[0].field").value("customerEmail"))
            .andExpect(jsonPath("$.validationErrors[0].message").value("customer email is required"));
    }

}