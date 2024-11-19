package com.main.board.user;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class User {


    private String user_id;
    private String password;
    private String name;
    private LocalDate create_date;


    public User(String user_id, String password, String name, LocalDate create_date) {
        this.user_id = user_id;
        this.password = password;
        this.name = name;
        this.create_date = create_date;
    }

    public static User create(String email, String password, String name, LocalDate create_date) {
        LocalDateTime now = DateTimeUtils.nowFromZone();
        return new User(email, password, name, birthDate, phone, role, now, now);
    }

}
