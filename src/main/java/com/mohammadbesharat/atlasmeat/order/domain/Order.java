package com.mohammadbesharat.atlasmeat.order.domain;

import com.mohammadbesharat.atlasmeat.checkout.domain.Checkout;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


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
    @OrderBy("id ASC")
    private Set<OrderItem> items = new HashSet<>();

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
    public Checkout getCheckout(){
        return checkout;
    }
    public AnimalType getAnimalType(){
        return animal;
    }
    public Set<OrderItem> getItems(){
        return items;
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
