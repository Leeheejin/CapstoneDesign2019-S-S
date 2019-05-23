package com.capstonedesign.backend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Water {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long wid;

    private Long uid;

    private Long cid;

    private Integer lastDrink;

    private ArrayList<Integer> waterDrinkData = new ArrayList<>();

    public void addWaterLog(Integer drinkLog) {
        this.waterDrinkData.add(drinkLog);
    }
}
