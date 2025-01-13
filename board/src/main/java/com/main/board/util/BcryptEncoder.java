package com.main.board.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//PasswordEncoder는 시큐리티에서 비밀번호 암호화를 적용하기위해 상속
@Component
public final class BcryptEncoder {

    /*
    작업팩터 동적할당 (작업팩터는 Bcrypt의 해시작업을 얼마나 복잡하게 할지 결정하는 값이다. 4~31사이의 값이 가능하다)
    작업팩터가 높을수록 해시작업이 복잡해지고 그만큼 시간이 오래걸린다. (기본값은 10)
    작업팩터가 높을수록 보안이 높아지지만 그만큼 시간이 오래걸린다
    */
    private static final int WORK_FACTOR = 12; // 12로 설정

    public String encrypt(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt(WORK_FACTOR));
    }


}
