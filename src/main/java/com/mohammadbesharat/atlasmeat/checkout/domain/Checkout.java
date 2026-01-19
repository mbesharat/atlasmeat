package com.mohammadbesharat.atlasmeat.checkout.domain;

import com.mohammadbesharat.atlasmeat.order.domain.Order;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "checkouts")
public class Checkout{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private CheckoutStatus status = CheckoutStatus.DRAFT;

    @OneToMany(
        mappedBy = "checkout",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Order> orders = new ArrayList<>();

    public void AddOrder(Order order){
        orders.add(order);
        order.setCheckout(this);
    }

    public void removeOrder(Order order){
        orders.remove(order);
        order.setCheckout(null);
    }


    public Long getId(){
        return id;
    }
    public String getName(){
        return customerName;
    }
    public String getEmail(){
        return customerEmail;
    }
    public String getPhone(){
        return customerPhone;
    }
    public CheckoutStatus getStatus(){
        return status;
    }
    public List<Order> getOrders(){
        return orders;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
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
    public void setStatus(CheckoutStatus status){
        this.status = status;
    }
}
