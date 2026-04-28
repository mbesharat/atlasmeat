package com.mohammadbesharat.atlasmeat.appointment;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AppointmentNotFoundIntegrationTest extends IntegrationTestBase {

    //return 404 when getAppointmentById with nonexistent id
    @Test
    void getAppointmentByIdWithNonExistentId() throws Exception{

        getJson("/appointments/{appointmentId}", 9999)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Appointment not found with id " + 9999));
    }

    //return 404 when setScheduleDate with nonexistent id
    @Test
    void setScheduledDateWithNonExistentId() throws Exception{
        patchWithParam(
                "/appointments/{appointmentId}/scheduled-date",
                "scheduledDate",
                "2026-06-15",
                9999
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Appointment not found with id " + 9999));

    }

    //return 404 when trying to updateAppointmentStatus with nonexistent id
    @Test
    void updateAppointmentStatusWithNonExistentId() throws Exception{
        patchJson(
                "/appointments/{appointmentId}/status",
                "\"SCHEDULED\"",
                9999
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Appointment not found with id " + 9999));

    }

    //return 404 when trying to set hanging weight with non existentId
    @Test
    void setHangingWeightWithNonExistentId() throws Exception{
        patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setHangingWeight(),
                9999
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Appointment not found with id " + 9999));
    }
}
