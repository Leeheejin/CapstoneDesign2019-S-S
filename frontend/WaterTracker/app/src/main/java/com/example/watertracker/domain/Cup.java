package com.example.watertracker.domain;

import lombok.Data;

@Data
public class Cup {

    private Long cid;

    private Long uid;

    private String cupName;

    private Integer cupWeight;
}