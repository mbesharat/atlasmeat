package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutValidationIntegrationTest extends IntegrationTestBase{


    //Missing values tests
    @Test
    void return400WhenMissingName() throws Exception{

        //POST /checkouts with missing customerName
        postJson("/checkouts", TestFixtures.createCheckoutMissingName())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("custoemr name is required"));
    }

    @Test 
    void return400WhenMissingPhone() throws Exception{

        //POST /checkouts with missing customerPhone
        postJson("/checkouts", TestFixtures.createCheckoutMissingPhone())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0]")
                .value("customer phone is required"));
    }

    @Test
    void return400WhenMissingEmail() throws Exception{

        //POST /checkouts with invalid customerEmail
        postJson("/checkouts", TestFixtures.createCheckoutMissingEmail())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer email is required"));
    }



    //Values violate constraints tests
    @Test
    void return400WhenNameTooLong() throws Exception{

        //POST /checkouts with a customerName that exceeds size constraint
        postJson("/checkouts", TestFixtures.createCheckoutNameTooLong())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer name must be less than or equal to 100 characters"));
    }

    @Test
    void return400WhenPhoneTooLong() throws Exception{

        //POST /checkouts with a customerPhone that exceeds size constraint
        postJson("/checkouts", TestFixtures.createCheckoutPhoneTooLong())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer phone must be less than or equal to 11 characters"));
    }

    @Test
    void return400WhenEmailWrongFormat() throws Exception{

        //POST /checkouts with a customerEmail that violates format constraint
        postJson("/checkouts", TestFixtures.createCheckoutWrongEmailFormat())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer email must be a valid email"));
    }


}