package com.example.watertracker.domain;

import lombok.Data;

@Data
public class Water {
    // 음수량 데이터가 들어오면 생성되는 객체

    private Long wid;
    //해당 음수 데이터의 식별 번호 (예를 들어, id가 1번인 사용자가 컵 id가 2번인 컵을 사용하여 물을 마셨을 때 해당 식별 번호로 불러오고 저장할 수 있음

    private Long uid;
    // 유저의 id

    private Long cid;
    // 컵 id

    private Float lastDrink;
    // 음수 데이터

    private Long lastDrinkDate;
    // 음수 시의 시간 데이터
}
