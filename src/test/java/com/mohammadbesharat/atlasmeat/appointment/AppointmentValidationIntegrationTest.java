package com.mohammadbesharat.atlasmeat.appointment;

import com.mohammadbesharat.atlasmeat.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static com.mohammadbesharat.atlasmeat.appointment.domain.AppointmentStatus.DROPPED_OFF;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentValidationIntegrationTest extends IntegrationTestBase {


    @Test
    void return400WhenRequiredFieldMissing() throws Exception {

        //missing name
        postJson("/appointments", AppointmentFixtures.createAppointmentMissingName())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer name is required"));

        //missing email
        postJson("/appointments", AppointmentFixtures.createAppointmentMissingEmail())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer email is required"));

        //missing phone
        postJson("/appointments", AppointmentFixtures.createAppointmentMissingPhone())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer phone is required"));

        //missing contact preference
        postJson("/appointments", AppointmentFixtures.createAppointmentMissingContactPreference())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("contact preference is required"));

        //missing animal type
        postJson("/appointments", AppointmentFixtures.createAppointmentMissingAnimalType())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("animal type is required"));
    }

    @Test
    void return400WhenInvalidValue() throws Exception {

        //name too long
        postJson("/appointments", AppointmentFixtures.createAppointmentNameTooLong())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer name must be less than or equal to 100 characters"));


        //phone too long
        postJson("/appointments", AppointmentFixtures.createAppointmentPhoneTooLong())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer phone must be less than or equal to 12 characters"));


        //invalid email
        postJson("/appointments", AppointmentFixtures.createAppointmentInvalidEmail())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("customer email must be a valid email"));

        //animal count below minimum
        postJson("/appointments", AppointmentFixtures.createAppointmentInvalidAnimalCount())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("animal count must be at least 1"));

        Long appointmentId = createAppointmentAndGetId();

        //test for invalid status enum
        patchJson("/appointments/{appointmentId}/status", AppointmentFixtures.setInvalidStatus(), appointmentId)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Invalid enum type. Valid values include: [REQUESTED, SCHEDULED, DROPPED_OFF, CUT_SHEET_OPEN, CANCELLED]"));

        //test for invalid scheduledDate
        patchJson("/appointments/{appointmentId}/scheduled-date", AppointmentFixtures.setInvalidDate(), appointmentId)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.message")
                        .value("Invalid date. Must in the format yyyy-MM-dd"));


        //tests for invalid hanging weight
        patchJson("/appointments/{appointmentId}/status", AppointmentFixtures.updateStatusDroppedOff(), appointmentId);

        //null hanging weight
        patchJson(
                "/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setInvalidHangingWeightNull(),
                appointmentId
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("hanging weight is required"));

        //zero hanging weight
        patchJson("/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setInvalidHangingWeightZero(),
                appointmentId
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("hanging weight must be greater than zero"));

        //hanging weight too long
        patchJson("/appointments/{appointmentId}/hanging-weight",
                AppointmentFixtures.setInvalidHangingWeightTooLong(),
                appointmentId
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.error")
                        .value("Bad Request"))
                .andExpect(jsonPath("$.validationErrors[0].message")
                        .value("hanging weight must have at most 5 digits before decimal and 2 after"));
    }



}
