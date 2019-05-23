package com.capstonedesign.backend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Water {

    @Id
    private Long uid;

    @Id
    private Long cid;

    private Integer lastDrink;

    private ArrayList<Integer> waterDrinkData = new ArrayList<>();

    public void addWaterLog(Integer drinkLog) {
        this.waterDrinkData.add(drinkLog);
    }
}
