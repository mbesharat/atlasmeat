package com.mohammadbesharat.atlasmeat.appointment;

public final class AppointmentFixtures {

    private AppointmentFixtures() {}

    public static String createValidAppointment(){
        return
        """
        {
            "customerName" : "John Doe",
            "customerEmail" : "john@email.com",
            "customerPhone" : "111-222-333",
            "contactPreference" : "EMAIL",
            "animalType" : "BEEF",
            "animalCount" : 5
        }
        """;
    }
}
