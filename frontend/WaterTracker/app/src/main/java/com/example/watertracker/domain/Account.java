package com.example.watertracker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Account {

    private Long id;
    // 유저 식별 id

    private Integer weight;
    // 유저 몸무게

    private Integer recommendDrink = 0;
    // 유저 추천 음수량

    private Float oneDrink = 0.0f;
    // 평균 한 모금

    private Float nowDrink = 0.0f;
    // 현재까지 마신 량

    private Cup currentCup;
    // 현재 사용하고 있는 컵 정보

    private List<Cup> cupList = new ArrayList<>();
    // 유저가 가지고 있는 전체 컵 데이터들

    private long accessTime;
    // 유저가 블루투스 페어링을 하는 하루 평균 시간

    private ArrayList<Float> waterLog = new ArrayList<>();
    // 음수 데이터가 쌓이는 배열

    private ArrayList<Long> waterLongDate = new ArrayList<>();
    // 음수시 시간 데이터가 쌓이는 배열

    public void addCupInList(Cup cup) {
        this.cupList.add(cup);
    }
    // 유저의 컵 리스트에 컵을 추가할 때 사용하는 메소드

    public void addLogInList(Float drinkData) {
        this.waterLog.add(drinkData);
    }
    // 유저가 음수 시 해당 음수량 데이터를 추가할 때 사용하는 메소드

    public void addDateInList(Long drinkDate) {
        this.waterLongDate.add(drinkDate);
    }
    // 유저가 음수 시 해당 시간 데이터를 추가할 때 사용하는 메소드
}