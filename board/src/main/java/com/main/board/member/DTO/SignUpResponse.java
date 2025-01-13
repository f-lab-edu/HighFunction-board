package com.main.board.member.DTO;

import com.main.board.member.Member;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter //Jackson 라이브러리는 객체를 JSON으로 변환할 때 기본적으로 getter 메서드를 사용
public class SignUpResponse {
    private final String email;
    private final LocalDateTime createAt;
    private final String message;

    public SignUpResponse(Member member) {
        this.email = member.getEmail();
        this.createAt = LocalDateTime.now();
        this.message = "회원가입이 완료되었습니다.";
    }
}
