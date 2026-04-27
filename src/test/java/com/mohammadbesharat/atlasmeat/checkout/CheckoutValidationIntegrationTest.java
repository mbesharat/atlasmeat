package com.mohammadbesharat.atlasmeat.checkout;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutValidationIntegrationTest extends IntegrationTestBase {


    //Missing values tests
    @Test
    void return400WhenMissingName() throws Exception{

        //POST /checkouts with missing customerName
        postJson("/checkouts", CheckoutFixtures.createCheckoutMissingName())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer name is required"));
    }

    @Test 
    void return400WhenMissingPhone() throws Exception{

        //POST /checkouts with missing customerPhone
        postJson("/checkouts", CheckoutFixtures.createCheckoutMissingPhone())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer phone is required"));
    }

    @Test
    void return400WhenMissingEmail() throws Exception{

        //POST /checkouts with missing customerEmail
        postJson("/checkouts", CheckoutFixtures.createCheckoutMissingEmail())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer email is required"));
    }

    @Test
    void return400WhenMissingNameAndEmail() throws Exception{

        //POST /checkouts with missing customerName and customerEmail
        postJson("/checkouts", CheckoutFixtures.createCheckoutMissingNameAndEmail())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors").isArray())
            .andExpect(jsonPath("$.validationErrors[?(@.field =='customerName')].message")
                .value("customer name is required"))
            .andExpect(jsonPath("$.validationErrors[?(@.field == 'customerEmail')].message")
                .value("customer email is required"));
    }

    @Test
    void return400WhenWhiteSpace() throws Exception{

        //POST /checkouts with white space in place of customerName
        postJson("/checkouts", CheckoutFixtures.createCheckoutWhitespaceName())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer name is required"));
    }



    //Values violate constraints tests
    @Test
    void return400WhenNameTooLong() throws Exception{

        //POST /checkouts with a customerName that exceeds size constraint
        postJson("/checkouts", CheckoutFixtures.createCheckoutNameTooLong())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer name must be less than or equal to 100 characters"));
    }

    @Test
    void return400WhenPhoneTooLong() throws Exception{

        //POST /checkouts with a customerPhone that exceeds size constraint
        postJson("/checkouts", CheckoutFixtures.createCheckoutPhoneTooLong())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer phone must be less than or equal to 12 characters"));
    }

    @Test
    void return400WhenEmailWrongFormat() throws Exception{

        //POST /checkouts with a customerEmail that violates format constraint
        postJson("/checkouts", CheckoutFixtures.createCheckoutWrongEmailFormat())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].message")
                .value("customer email must be a valid email"));
    }




}