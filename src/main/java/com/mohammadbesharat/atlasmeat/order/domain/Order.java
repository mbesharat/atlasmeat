package com.mohammadbesharat.atlasmeat.order.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mohammadbesharat.atlasmeat.future.availability.DepositStatus;



@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate scheduledDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private DepositStatus depositStatus;
    // private Integer depositCents = 0;
    // private String stripePaymentIntentId;
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

    // public LocalDate getScheduledDate(){
    //     return scheduledDate;
    // }

    // public OrderStatus getStatus(){
    //     return status;
    // }

    // public DepositStatus getDepositStatus(){
    //     return depositStatus;
    // }

    // public Integer getDepositCents(){
    //     return depositCents;
    // }

    // public String getStripePaymentIntentId(){
    //     return stripePaymentIntentId;
    // }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getCancelledAt(){
        return cancelledAt;
    }

    // public LocalDateTime getRefundedAt(){
    //     return refundedAt;
    // }

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

    // public void setScheduledDate(LocalDate date){
    //     this.scheduledDate = date;
    // }

    // public void setStatus(OrderStatus status){
    //     this.status = status;
    // }

    // public void setDepositStatus(DepositStatus deposit){
    //     this.depositStatus = deposit;
    // }

    // public void setDepositCents(int cents){
    //     this.depositCents = cents;
    // }

    // public void setStripePaymentIntentId(String stripeId){
    //     this.stripePaymentIntentId = stripeId;
    // }

    public void setCreatedAt(LocalDateTime created){
        this.createdAt = created;
    }

    public void setCancelledAt(LocalDateTime cancelled){
        this.cancelledAt = cancelled;
    }

    // public void setRefundedAt(LocalDateTime refunded){
    //     this.refundedAt = refunded;
    // }

    public void setOrderDetails(String details){
        this.orderDetails = details;
    }
  
   
}
