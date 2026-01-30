package com.mohammadbesharat.atlasmeat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mohammadbesharat.atlasmeat.order.domain.AnimalType;
import com.mohammadbesharat.atlasmeat.order.domain.Cut;
import com.mohammadbesharat.atlasmeat.order.repo.CutRepository;
import com.mohammadbesharat.atlasmeat.order.repo.OrderItemRepository;

class CheckoutIntegrationTest extends IntegrationTestBase{

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired CutRepository cutRepository;
    @Autowired OrderItemRepository orderItemRepository;

    @BeforeEach
    void seedCuts(){
        cutRepository.deleteAll();

        Cut ribeye = new Cut();
        ribeye.setAnimal(AnimalType.BEEF);
        ribeye.setCode("RIBEYE");
        ribeye.setDisplayName("Ribeye");
        cutRepository.save(ribeye);

        Cut brisket = new Cut();
        brisket.setAnimal(AnimalType.BEEF);
        brisket.setCode("BRISKET");
        brisket.setDisplayName("Brisket");
        cutRepository.save(brisket);
    }

    @Test
    void fullLifecycle_createCheckout_addOrder_patchItem_deleteItem() throws Exception{

        //POST /checkouts
        String createCheckoutJson = """
        {
            "customerName" : "John Doe",
            "customerPhone" : "303-555-1111",
            "customerEmail" : "john@example.com"
        }
        """;

        String checkoutResponse = mvc.perform(post("/checkouts")
                .contentType("application/json")
                .content(createCheckoutJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.checkoutId").exists())
            .andExpect(jsonPath("$.status").value("DRAFT"))
            .andReturn().getResponse().getContentAsString();

        long checkoutId = mapper.readTree(checkoutResponse).get("checkoutId").asLong();

        //Fetch cut ids to use in create order payload
        Long ribeyeId = cutRepository.findAll().stream()
            .filter(c -> "Ribeye".equals(c.getDisplayName()))
            .findFirst()
            .orElseThrow()
            .getId();

        //POST /checkouts/{checkoutId}/orders
        String addOrderJson = """
        {
            "animal" : "BEEF",
            "items" : [{"cutId" : %d, "quantity" : 2}]
        }
        """.formatted(ribeyeId);

        String afterAddOrder = mvc.perform(post("/checkouts/{checkoutId}/orders", checkoutId)
                .contentType("application/json")
                .content(addOrderJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders[0].id").exists())
            .andExpect(jsonPath("$.orders[0].animal").value("BEEF"))
            .andExpect(jsonPath("$.orders[0].items[0].orderItemId").exists())
            .andExpect(jsonPath("$.orders[0].items[0].quantity").value(2))
            .andReturn().getResponse().getContentAsString();

        var tree = mapper.readTree(afterAddOrder);
        long orderId = tree.get("orders").get(0).get("id").asLong();
        long orderItemId = tree.get("orders").get(0).get("items").get(0).get("orderItemId").asLong();

        //Patch item quantity
        String patchItemJson = """
        {
            "quantity" : 5
        }
        """;

        mvc.perform(patch("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
                checkoutId, orderId, orderItemId)
                .contentType("application/json")
                .content(patchItemJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders[0].items[0].quantity").value(5));

        //DELETE item
        mvc.perform(delete("/checkouts/{checkoutId}/orders/{orderId}/items/{orderItemId}", 
                checkoutId, orderId, orderItemId))
            .andExpect(status().isNoContent());

        //GET checkout and assert items empty
        mvc.perform(get("/checkouts/{checkoutId}", checkoutId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders[0].items").isArray())
            .andExpect(jsonPath("$.orders[0].items.length()").value(0));
    }
}