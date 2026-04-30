package com.mohammadbesharat.atlasmeat.appointment;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentConflictIntegrationTest extends IntegrationTestBase {

    @Test
    void return409RequestedToDroppedOff() throws Exception {

        Long appointmentId = createAppointmentAndGetId();

        patchJson(
                "/appointments/{appointmentId}/status",
                AppointmentFixtures.updateStatusDroppedOff(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("REQUESTED cannot be changed to DROPPED_OFF"));
    }

    @Test
    void return409DroppedOffToCancelled() throws Exception {

        Long appointmentId = createAppointmentAndGetId();
        advanceStatus(appointmentId, "SCHEDULED");
        advanceStatus(appointmentId, "DROPPED_OFF");

        patchJson(
                "/appointments/{appointmentId}/status",
                AppointmentFixtures.updateStatusCancelled(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("DROPPED_OFF cannot be changed to CANCELLED"));
    }

    @Test
    void return409CancelledToScheduled() throws Exception {

        Long appointmentId = createAppointmentAndGetId();
        advanceStatus(appointmentId, "SCHEDULED");
        advanceStatus(appointmentId, "CANCELLED");

        patchJson(
                "/appointments/{appointmentId}/status",
                AppointmentFixtures.updateStatusScheduled(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("CANCELLED cannot be changed to SCHEDULED"));
    }

    @Test
    void return409CutSheetOpenToScheduled() throws Exception {

        Long appointmentId = createAppointmentAndGetId();
        advanceStatus(appointmentId, "SCHEDULED");
        advanceStatus(appointmentId, "DROPPED_OFF");
        advanceStatus(appointmentId, "CUT_SHEET_OPEN");

        patchJson(
                "/appointments/{appointmentId}/status",
                AppointmentFixtures.updateStatusScheduled(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("CUT_SHEET_OPEN cannot be changed to SCHEDULED"));
    }

    @Test
    void return409SettingHangingWeightToInvalidStatus() throws Exception {

        Long appointmentId = createAppointmentAndGetId();

        //setHangingWeight on REQUESTED
        patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setHangingWeight(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("Appointment with id " + appointmentId + " with status REQUESTED cannot have hanging weight set"));


        //setHangingWeight on SCHEDULED
        advanceStatus(appointmentId, "SCHEDULED");
        patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setHangingWeight(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("Appointment with id " + appointmentId + " with status SCHEDULED cannot have hanging weight set"));

        //setHangingWeight on CANCELLED
        advanceStatus(appointmentId, "CANCELLED");
        patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setHangingWeight(),
                appointmentId
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status")
                        .value(409))
                .andExpect(jsonPath("$.error")
                        .value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("Appointment with id " + appointmentId + " with status CANCELLED cannot have hanging weight set"));
    }

    private void advanceStatus(Long appointmentId, String status) throws Exception {
        patchJson("/appointments/{appointmentId}/status", AppointmentFixtures.setStatus(status), appointmentId);
    }
}
