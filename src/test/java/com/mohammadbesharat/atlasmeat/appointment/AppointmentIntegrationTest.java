package com.mohammadbesharat.atlasmeat.appointment;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AppointmentIntegrationTest extends IntegrationTestBase {

    @Test
    void fullLifeCycle_createAppointment_addScheduledDate_UpdateStatus() throws Exception {

        //POST - create appointment
        ResultActions createResult = createAppointment();
        assertBaseFields(createResult);
        createResult
                .andExpect(jsonPath("$.status").value("REQUESTED"))
                .andExpect(jsonPath("$.scheduledDate").doesNotExist())
                .andExpect(jsonPath("$.checkoutId").doesNotExist());

        Long appointmentId = mapper.readTree(
                createResult.andReturn().getResponse().getContentAsString()
        ).get("id").asLong();


        //GET appointment using id
        ResultActions getResult = getAppointmentById(appointmentId);
        assertBaseFields(getResult);
        getResult
                .andExpect(jsonPath("$.status").value("REQUESTED"))
                .andExpect(jsonPath("$.scheduledDate").doesNotExist())
                .andExpect(jsonPath("$.checkoutId").doesNotExist());


        //PATCH - set appointment date
        ResultActions setDateResult = patchWithParam(
                "/appointments/{appointmentId}/scheduled-date",
                "scheduledDate",
                "2026-06-15",
                appointmentId
        );
        assertBaseFields(setDateResult);
        setDateResult.andExpect(jsonPath("$.scheduledDate").value("2026-06-15"));


        //PATCH - update appointment status REQUESTED -> SCHEDULED -> DROPPED OFF -> CUT SHEET OPEN
        ResultActions scheduleResult = patchJson(
                "/appointments/{appointmentId}/status",
                "\"SCHEDULED\"",
                appointmentId
        );
        assertBaseFields(scheduleResult);
        scheduleResult
                .andExpect(jsonPath("$.scheduledDate").value("2026-06-15"))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));


        //SCHEDULED -> DROPPED OFF
        ResultActions dropOffResult = patchJson(
                "/appointments/{appointmentId}/status",
                "\"DROPPED_OFF\"",
                appointmentId
        );
        assertBaseFields(dropOffResult);
        dropOffResult
                .andExpect(jsonPath("$.scheduledDate").value("2026-06-15"))
                .andExpect(jsonPath("$.status").value("DROPPED_OFF"));


        //PATCH - setHangingWeight
        ResultActions hangingWeightResult = patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setHangingWeight(),
                appointmentId);
        assertBaseFields(hangingWeightResult);
        hangingWeightResult
                .andExpect(jsonPath("$.scheduledDate").value("2026-06-15"))
                .andExpect(jsonPath("$.status").value("DROPPED_OFF"))
                .andExpect(jsonPath("$.hangingWeight").value(AppointmentFixtures.setHangingWeight()));


        //DROPPED OFF -> CUT SHEET OPEN
        ResultActions cutSheetResult = patchJson(
                "/appointments/{appointmentId}/status",
                "\"CUT_SHEET_OPEN\"",
                appointmentId
        );
        assertBaseFields(cutSheetResult);
        cutSheetResult
                .andExpect(jsonPath("$.scheduledDate").value("2026-06-15"))
                .andExpect(jsonPath("$.status").value("CUT_SHEET_OPEN"));



    }

    private void assertBaseFields(ResultActions actions) throws Exception {
        actions
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.customerEmail").value("john@email.com"))
                .andExpect(jsonPath("$.customerPhone").value("111-222-333"))
                .andExpect(jsonPath("$.contactPreference").value("EMAIL"))
                .andExpect(jsonPath("$.animalType").value("BEEF"))
                .andExpect(jsonPath("$.animalCount").value(5));
    }

}
