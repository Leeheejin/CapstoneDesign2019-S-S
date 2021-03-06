package com.capstonedesign.backend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer weight;

    private Integer recommendDrink = 0;

    private Float oneDrink = 0.0f;

    private Float nowDrink = 0.0f;

    @OneToOne(targetEntity = Cup.class, fetch = FetchType.EAGER)
    private Cup currentCup;

    @OneToMany(targetEntity=Cup.class, fetch=FetchType.EAGER)
    private List<Cup> cupList = new ArrayList<>();

    private long accessTime;

    public void addCupInList(Cup cup) {
        this.cupList.add(cup);
    }

    private ArrayList<Float> waterLog = new ArrayList<>();

    private ArrayList<Long> waterLongDate = new ArrayList<>();

    public void addLogInList(Float drinkData) {
        this.waterLog.add(drinkData);
    }

    public void addDateInList(Long drinkDate) {
        this.waterLongDate.add(drinkDate);
    }
}
