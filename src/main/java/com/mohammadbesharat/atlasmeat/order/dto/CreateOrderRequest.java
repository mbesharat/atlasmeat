package com.mohammadbesharat.atlasmeat.order.dto;


// import java.time.LocalDate;

public class CreateOrderRequest {
    
    public String customerName;
    public String customerEmail;
    public String customerPhone;
    public String orderDetails;
   
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

}
