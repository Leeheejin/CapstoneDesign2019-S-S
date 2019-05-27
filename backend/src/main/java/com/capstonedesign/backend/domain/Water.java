package com.capstonedesign.backend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Water {

    @Id
    private Long wid;

    private Long uid;

    private Long cid;

    private Integer lastDrink;

    private Long lastDrinkDate;
}
