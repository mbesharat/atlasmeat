package com.mohammadbesharat.atlasmeat.order.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cut_id", nullable = false)
    private Cut cut;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;


    public void setOrder(Order order){
        this.order = order;
    }
    public void setCut(Cut cut){
        this.cut = cut;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public Long getId(){
        return id;
    }
    public Order getOrder(){
        return order;
    }
    public Cut getCut(){
        return cut;
    }
    public int getQuantity(){
        return quantity;
    }
    

}
