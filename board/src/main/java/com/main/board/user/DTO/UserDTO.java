package com.main.board.user.DTO;

import com.main.board.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String user_id;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate create_date;



    public UserDTO(String user_id, String password, String name, LocalDate create_date) {
        this.user_id = validateId(user_id);
        this.password = validatePaswd(password);
        this.name = name;
        this.create_date = create_date;
    }

    public String validateId(String user_id) {
        if (user_id.length() < 5 || user_id.length() > 20) {
            throw new IllegalArgumentException("아이디는 5자 이상 20자 이하로 입력해주세요.");
        }
        return user_id;
    }

    public String validatePaswd(String password) {
        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }
        return password;
    }

    public User toUserEntity(String pwd) {
        return new User(this.user_id, pwd, this.name, this.create_date);
    }


    @Getter
    @NoArgsConstructor
    public static class SignUpResponse {
        private String user_id;
        private String password;
        private String name;
        private LocalDate create_date;

        public SignUpResponse(User user) {
            this.user_id = user.getUser_id();
            this.password = user.getPassword();
            this.name = user.getName();
            this.create_date = user.getCreate_date();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoginResponse {
        private String user_id;
        private String password;
        private String name;
        private LocalDate create_date;

        public LoginResponse(User user) {
            this.user_id = user.getUser_id();
            this.password = user.getPassword();
            this.name = user.getName();
            this.create_date = user.getCreate_date();
        }
    }

}
