package com.mohammadbesharat.atlasmeat.dto;


// import java.time.LocalDate;

public class CreateOrderRequest {
    
    public String customerName;
    public String customerEmail;
    public String customerPhone;
    public String orderDetails;
    // public LocalDate scheduledDate;

   
    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }

    public String getOrderDetails(){
        return orderDetails;
    }

    // public LocalDate getScheduledDate(){
    //     return scheduledDate;
    // }


    public void setCustomerName(String name){
        this.customerName = name;
    }

    public void setCustomerEmail(String email){
        this.customerEmail = email;
    }

    public void setCustomerPhone(String phone){
        this.customerPhone = phone;
    }

    public void setOrderDetails(String details){
        this.orderDetails = details;
    }

    // public void setScheduledDate(LocalDate date){
    //     this.scheduledDate = date;
    // }
    
}
