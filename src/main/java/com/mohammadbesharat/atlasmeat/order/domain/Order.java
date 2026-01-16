package com.mohammadbesharat.atlasmeat.order.domain;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderDetails;

    @ManyToOne(fetch = FetchType.Lazy, optional = false)
    @JoinColumn(name = "checkout_id", nullable = false)
    private Checkout checkout;  

    @Enumerated(EnumType.STRING)
    @Column(nullabe = false)
    private CattleType cattle;

    @OneToMany(
        mappedBy = "order",
        cascade = CasecadeType.All,
        orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item){
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item){
        items.remove(item);
        item.SetOrder(null);
    }

    public Order(){}
    public Long getId(){
        return id;
    }


    public String getOrderDetails(){
        return orderDetails;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public void setOrderDetails(String details){
        this.orderDetails = details;
    }

    public void setCheckout(Checkout checkout){
        this.checkout = checkout;
    }
  
   
}
