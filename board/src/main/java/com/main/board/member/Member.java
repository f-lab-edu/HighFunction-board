package com.main.board.member;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class Member {


    private String email;
    private String encryptPwd;
    private LocalDateTime createAt;


    public Member(String email, String encryptPwd) {
        this.email = email;
        this.encryptPwd = encryptPwd;
        this.createAt = LocalDateTime.now();
    }


}
