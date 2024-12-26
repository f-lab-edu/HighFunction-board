package com.main.board.member.DTO;

import com.main.board.member.Member;

import java.time.LocalDate;

public class SignUpResponse {
    private String memberId;
    private String name;
    private LocalDate createDate;

    public SignUpResponse(Member member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.createDate = member.getCreateDate();
    }
}
