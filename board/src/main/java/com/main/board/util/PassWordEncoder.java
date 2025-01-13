package com.main.board.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassWordEncoder implements PasswordEncoder {

    private static final int WORK_FACTOR = 12;

        @Override
        public String encode(CharSequence rawPassword) {
            return BCrypt.hashpw(String.valueOf(rawPassword),BCrypt.gensalt(WORK_FACTOR));
        }
        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return BCrypt.checkpw(rawPassword.toString(),encodedPassword);
        }


}
