package com.example.watertracker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Account {

    private Long id;

    private Integer weight;

    private Integer recommendDrink = 0;

    private Integer oneDrink = 0;

    private Integer nowDrink = 0;

    private Cup currentCup;

    private List<Cup> cupList = new ArrayList<>();

    private long accessTime;

    public void addCupInList(Cup cup) {
        this.cupList.add(cup);
    }

    private ArrayList<Integer> waterLog = new ArrayList<>();

    private ArrayList<Long> waterLongDate = new ArrayList<>();

    public void addLogInList(Integer drinkData) {
        this.waterLog.add(drinkData);
    }

    public void addDateInList(Long drinkDate) {
        this.waterLongDate.add(drinkDate);
    }
}