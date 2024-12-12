package com.main.board.member.DTO;

import com.main.board.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class MemberDTO {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate createDate;



    public MemberDTO(String userId, String password, String name, LocalDate createDate) {
        this.userId = validateId(userId);
        this.password = validatePaswd(password);
        this.name = name;
        this.createDate = createDate;
    }

    public String validateId(String userId) {
        if (userId.length() < 5 || userId.length() > 20) {
            throw new IllegalArgumentException("아이디는 5자 이상 20자 이하로 입력해주세요.");
        }
        return userId;
    }

    public String validatePaswd(String password) {
        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }
        return password;
    }

    public Member toMemberEntity(String pwd) {
        return new Member(this.userId, pwd, this.name, this.createDate);
    }

}
