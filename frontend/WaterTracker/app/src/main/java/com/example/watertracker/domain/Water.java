package com.example.watertracker.domain;

import lombok.Data;

@Data
public class Water {

    private Long wid;

    private Long uid;

    private Long cid;

    private Integer lastDrink;

    private Long lastDrinkDate;
}
