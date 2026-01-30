package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutValidationIntegrationTest extends IntegrationTestBase{

    @Autowired MockMvc mvc;
    
    @Test
    void testMissingEmailValidation() throws Exception{

        //POST /checkouts with invalid email
        String createCheckoutJson = """
        {
            "customerName" : "John Doe",
            "customerPhone" : "303-555-111"
        }
        """;

        mvc.perform(post("/checkouts")
                .contentType("application/json")
                .content(createCheckoutJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith("application/json"))
            .andExpect(jsonPath("$.validationErrors").isArray())
            .andExpect(jsonPath("$.validationErrors[0].field").value("customerEmail"))
            .andExpect(jsonPath("$.validationErrors[0].message").value("customer email is required"));
    }
}