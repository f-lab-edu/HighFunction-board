package com.main.board.member.DTO;

import com.main.board.member.Member;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효한 이메일 형식이아닙니다")
    private String email;

    @NotEmpty
    @Size(min=6, max=20, message = "비밀번호는 6자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$", message = "비밀번호는 소문자,숫자,특수문자 를포함해야 합니다.")
    private String rawPassword;

    private LocalDateTime createAt;



    public SignupRequest(String email, String rawPassword) {
        this.email = email;
        this.rawPassword = validateRawPassword(rawPassword);
        this.createAt = LocalDateTime.now();
    }


    public String validateRawPassword(String rawPassword) {
        if (rawPassword.length() < 8 || rawPassword.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }
        return rawPassword;
    }

    public Member toMemberEntity(String encryptPwd) {
        return new Member(this.email, encryptPwd);
    }

}
