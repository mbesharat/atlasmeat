package com.mohammadbesharat.atlasmeat.appointment;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentErrorContractIntegrationTest extends IntegrationTestBase {

    @Test
    void return400ErrorShape_MissingName() throws Exception {

        postJson("/appointments", AppointmentFixtures.createAppointmentMissingName())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/appointments"))
                .andExpect(jsonPath("$.validationErrors").exists());
    }

    @Test
    void return404ErrorShape_AppointmentNotFound() throws Exception {

        getJson("/appointments/{appointmentId}", 9999)
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/appointments/9999"))
                .andExpect(jsonPath("$.validationErrors").exists());
    }

    @Test
    void return409RequestedToDroppedOff() throws Exception {

        Long appointmentId = createAppointmentAndGetId();

        patchJson("/appointments/{appointmentId}/status", AppointmentFixtures.updateStatusDroppedOff(), appointmentId)
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/appointments/" + appointmentId + "/status"))
                .andExpect(jsonPath("$.validationErrors").exists());
    }
}
