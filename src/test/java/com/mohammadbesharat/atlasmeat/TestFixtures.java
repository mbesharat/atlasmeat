package com.mohammadbesharat.atlasmeat;


public final class TestFixtures {

    private TestFixtures(){}

    public static String createValidCheckout(){
        return 
        """
        {
            "customerName" : "John Doe",
            "customerPhone" : "111-222-3333",
            "customerEmail" : "john@email.com"
        }
        """;
    }

    public static String createCheckoutMissingName(){
        return
        """
        {
            "customerPhone" : "111-222-3333",
            "customerEmail" : "john@email.com"
        }        
        """;
    }

    public static String createCheckoutNameTooLong(){
        return
        """
        {
            "customerName" : "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj",
            "customerPhone" : "111-222-3333",
            "customerEmail" : "john@email.com"
        }        
        """;
    }

    public static String createCheckoutMissingPhone(){
        return
        """
        {
            "customerName" : "John Doe",
            "customerEmail" : "john@email.com"
        }        
        """;
    }

    public static String createCheckoutPhoneTooLong(){
        return
        """
        {
            "customerName" : "John Doe",
            "customerPhone" : "00-111-222-3333",
            "customerEmail" : "john@email.com"
        }        
        """;
    }

    public static String createCheckoutMissingEmail(){
        return 
        """
        {
            "customerName" : "John Doe",
            "customerPhone" : "111-222-3333"
        }     
        """;
    }

    public static String createCheckoutWrongEmailFormat(){
        return
        """
        {
            "customerName" : "John Doe",
            "customerPhone" : "111-222-3333",
            "customerEmail" : "john@email"
        }
        """;
    }

    public static String createCheckoutMissingNameAndEmail(){
        return
        """
        {
            "customerPhone" : "111-222-3333"
        }        
        """;
    }

    public static String createCheckoutWhitespaceName(){
        return
        """
        {
            "customerName" : " ",
            "customerPhone" : "111-222-3333",
            "customerEmail" : "john@email.com"
        }
        """;
    }

    public static String addBeefOrder(long cutId, int quantity){
        return
        """
        {
            "animal" : "BEEF",
            "items" : [{"cutId" : %d, "quantity" : %d}]
        }
        """.formatted(cutId, quantity);
    }
    
    public static String patchItemQuantity(int quantity){
        return
        """
        {
            "quantity" : %d
        }        
        """.formatted(quantity);
    }

    public static String updateCheckoutStatus(String status){
        return
        """
        {
            "status" : "%s"
        }        
        """.formatted(status);
    }

    public static String updateCheckoutSubmitted(){
        return updateCheckoutStatus("SUBMITTED");
    }

    public static String updateCheckoutPaid(){
        return updateCheckoutStatus("PAID");
    }

    public static String updateCheckoutCancelled(){
        return updateCheckoutStatus("CANCELLED");
    }


}
