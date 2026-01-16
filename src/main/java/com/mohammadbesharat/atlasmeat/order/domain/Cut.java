package com.mohammadbesharat.atlasmeat.order.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "cuts")
public class Cut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CattleType cattletype;

    private String code;
    private String displayName;

    public CattleType getCattleType(){
        return cattletype;
    }
    public String getCode(){
        return code;
    }
    public String getDisplayName(){
        return displayName;
    }



    public void setCattle(CattleType cattle){
        this.cattletype = cattle;
    }
    public void setCode(String code){
        this.code = code;
    }
    public void setDisplayName(String name){
        this.displayName = name;
    }


}
