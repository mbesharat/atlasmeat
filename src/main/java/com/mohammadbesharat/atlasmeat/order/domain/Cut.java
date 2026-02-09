package com.mohammadbesharat.atlasmeat.order.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "cuts")
public class Cut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AnimalType animal;

    private String code;
    private String displayName;


    public Long getId(){
        return id;
    }
    public AnimalType getAnimalType(){
        return animal;
    }
    public String getCode(){
        return code;
    }
    public String getDisplayName(){
        return displayName;
    }



    public void setAnimal(AnimalType animal){
        this.animal = animal;
    }
    public void setCode(String code){
        this.code = code;
    }
    public void setDisplayName(String name){
        this.displayName = name;
    }


}
