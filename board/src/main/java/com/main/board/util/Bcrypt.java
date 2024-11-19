package com.main.board.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public final class Bcrypt {

    public String encrypt(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public boolean isMatch(String password, String hashed) {
        return BCrypt.checkpw(password,hashed);
    }


}
