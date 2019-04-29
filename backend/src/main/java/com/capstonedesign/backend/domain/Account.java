package com.capstonedesign.backend.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long pid;

    @Column(nullable = false, unique = true)
    private String userMid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer weight;

    private Integer recommendDrink;

    private Integer oneDrink;

    private Cup currentCup;

    private Long registerDate;

    private List<Cup> cupList;

    //Check Password
    //TODO : NOT Encrypted!!!!
    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
