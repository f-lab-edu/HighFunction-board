package com.main.board.member;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class Member {


    private String memberId;
    private String password;
    private String name;
    private LocalDate createDate;


    public Member(String memberId, String password, String name, LocalDate createDate) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.createDate = createDate;
    }


}
