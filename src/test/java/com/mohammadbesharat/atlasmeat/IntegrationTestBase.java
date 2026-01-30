package com.mohammadbesharat.atlasmeat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    protected record OrderIds(long orderId, long orderItemId){}


    @Autowired protected MockMvc mvc;
    @Autowired protected ObjectMapper mapper;
    
    static PostgreSQLContainer<?> postgres; 
    static{
            postgres = new PostgreSQLContainer<>("postgres:16-alpine");
            postgres.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    //TRANSPORT HELPERS
    protected ResultActions postJson(String urlTemplate, String body, Object... uriVars) throws Exception{
        return mvc.perform(post(urlTemplate, uriVars)
                .contentType("application/json")
                .content(body));
    }

    protected String postJsonAndReturnBody(String urlTemplate, String body, Object... uriVars) throws Exception{
        return postJson(urlTemplate, body, uriVars)
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    protected ResultActions getJson(String urlTemplate, Object... uriVars) throws Exception {
        return mvc.perform(get(urlTemplate, uriVars));
    }

    protected ResultActions patchJson(String urlTemplate, String body, Object... uriVars) throws Exception{
        return mvc.perform(patch(urlTemplate, uriVars)
                .contentType("application/json")
                .content(body));
    }

    protected ResultActions deleteJson(String urlTemplate, Object... uriVars) throws Exception{
        return mvc.perform(delete(urlTemplate, uriVars));
    }




    //DRIVER HELPERS
    protected long createCheckoutAndGetId() throws Exception{

        String response = postJson("/checkouts", 
            TestFixtures.createValidCheckout())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.checkoutId").exists())
            .andExpect(jsonPath("$.status").value("DRAFT"))
            .andReturn().getResponse().getContentAsString();
        return mapper.readTree(response).get("checkoutId").asLong();
    }

    protected OrderIds addBeefOrderAndGetIds(long checkoutId, long cutId, int quantity) throws Exception{
        String response = postJson("/checkouts/{checkoutId}/orders", 
                            TestFixtures.addBeefOrder(cutId, quantity), checkoutId).andExpect(status().isOk())
                            .andExpect(jsonPath("$.orders[0].id").exists())
                            .andExpect(jsonPath("$.orders[0].animal").value("BEEF"))
                            .andExpect(jsonPath("$.orders[0].items[0].orderItemId").exists())
                            .andExpect(jsonPath("$.orders[0].items[0].quantity").value(quantity))
                            .andReturn()
                            .getResponse().
                            getContentAsString();
        var tree = mapper.readTree(response);
        long orderId = tree.at("/orders/0/id").asLong();
        long orderItemId = tree.at("/orders/0/items/0/orderItemId").asLong();
        return new OrderIds(orderId, orderItemId);
    }

    protected ResultActions getCheckout(long checkoutId) throws Exception{
        return getJson("/checkouts/{checkoutId}", checkoutId)
                .andExpect(status().isOk());
    }

}