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

    public static String createCheckoutMissingEmail(){
        return 
        """
        {
            "customerName" : "John Doe",
            "customerPhone" : "111-222-3333"
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
}
