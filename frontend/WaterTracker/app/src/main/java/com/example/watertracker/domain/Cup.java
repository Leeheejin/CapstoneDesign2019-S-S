package com.example.watertracker.domain;

import lombok.Data;

@Data
public class Cup {
    // 컵 정보를 담는 객체, 컵 생성, 수정, 삭제 시 사용

    private Long cid;
    // 컵 식별 id

    private Long uid;
    // 해당 컵을 가지고 있는 유저의 id

    private String cupName;
    // 컵 이름

    private Integer cupWeight;
    // 컵 무게
}