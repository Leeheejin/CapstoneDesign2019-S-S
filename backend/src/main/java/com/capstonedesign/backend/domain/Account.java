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

    @Column(nullable = false, unique = true)
    private String userMid;

    @Column(nullable = false)
    private Integer weight;

    private Integer recommendDrink;

    private Integer oneDrink;

    @OneToOne(targetEntity = Cup.class, fetch = FetchType.LAZY)
    private Cup currentCup;

    @OneToMany(targetEntity=Cup.class, fetch=FetchType.LAZY)
    private List<Cup> cupList = new ArrayList<>();

    public void addCupInList(Cup cup) {
        this.cupList.add(cup);
    }
}
