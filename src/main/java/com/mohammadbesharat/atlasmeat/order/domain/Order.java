package com.mohammadbesharat.atlasmeat.order.domain;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "checkout_id", nullable = false)
    private Checkout checkout;  

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalType animal;

    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item){
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item){
        items.remove(item);
        item.setOrder(null);
    }

    public Order(){}


    public Long getId(){
        return id;
    }
    public AnimalType getAnimalType(){
        return animal;
    }
    


    public void setId(Long id){
        this.id = id;
    }
    public void setCheckout(Checkout checkout){
        this.checkout = checkout;
    }
    public void setAnimal(AnimalType animal){
        this.animal = animal;
    }
  
   
}
