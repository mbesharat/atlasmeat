package com.mohammadbesharat.atlasmeat.order.domain;

import jakarta.persistence.*;

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
