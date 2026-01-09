package com.mohammadbesharat.atlasmeat.order.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;




@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
    private String orderDetails;

    public Order(){}
    public Long getId(){
        return id;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getCancelledAt(){
        return cancelledAt;
    }

    public String getOrderDetails(){
        return orderDetails;
    }
    
    public void setId(Long id){
        this.id = id;
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

    public void setCreatedAt(LocalDateTime created){
        this.createdAt = created;
    }

    public void setCancelledAt(LocalDateTime cancelled){
        this.cancelledAt = cancelled;
    }

    public void setOrderDetails(String details){
        this.orderDetails = details;
    }
  
   
}
