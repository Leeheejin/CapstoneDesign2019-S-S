package com.capstonedesign.backend.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Login {

    @NotEmpty(message="아이디를 입력해주세요.")
    private String userMid;

    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String password;
    private boolean rememberId;
}
