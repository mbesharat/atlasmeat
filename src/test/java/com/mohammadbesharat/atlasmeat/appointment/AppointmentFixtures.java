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

    public static String createAppointmentMissingName(){
        return
            """
            {
                "customerEmail" : "john@email.com",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentMissingEmail(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentMissingPhone(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "john@email.com",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentInvalidEmail(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "johnemail.com",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentMissingContactPreference(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "john@email.com",
                "customerPhone" : "111-222-333",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentMissingAnimalType(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "john@email.com",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentNameTooLong(){
        return
            """
            {
                "customerName" : "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj",
                "customerEmail" : "john@email.com",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentPhoneTooLong(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "john@email.com",
                "customerPhone" : "1111111111111",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 5
            }
            """;
    }

    public static String createAppointmentInvalidAnimalCount(){
        return
            """
            {
                "customerName" : "John Doe",
                "customerEmail" : "john@email.com",
                "customerPhone" : "111-222-333",
                "contactPreference" : "EMAIL",
                "animalType" : "BEEF",
                "animalCount" : 0
            }
            """;
    }

    public static String setHangingWeight(){
        return
            """
            {
                "hangingWeight" : 25.5
            }
            """;
    }

    public static String setInvalidHangingWeightNull(){
        return
                """
                {
                    "hangingWeight" : null
                }
               """;
    }

    public static String setInvalidHangingWeightZero(){
        return
                """
                {
                    "hangingWeight" : 0
                }
                """;
    }

    public static String setInvalidHangingWeightTooLong(){
        return
                """
                {
                    "hangingWeight" : 9999999999.9999999999
                }
                """;
    }
}
