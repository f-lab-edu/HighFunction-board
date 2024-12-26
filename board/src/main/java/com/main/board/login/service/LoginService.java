package com.main.board.login.service;

import com.main.board.login.DTO.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    public void login(LoginRequest loginRequest) {
            /*
            인증 요청
            토큰이라고 해서 토큰방식을 사용하는것이 아니고
            Spring Security에서 토큰 기반의 인증을 수행하는 객체이다
            Spring Secutiry 내부 에서 사용자 인증 정보를 담기위한 객체이다
             */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getRawPassword()); //토큰생성
            /*
            1. authenticationManager가 Provider에게 인증을 위임
            2. config에 설정한 내용대로 UserDetailsService를 통해 유저정보를 가져온다
            3. 입력된비밀번호와 가져온정보의 비밀번호를 비교하여 인증에 성공하면 Authentication 객체를 생성하여 리턴
             */
        Authentication authentication = authenticationManager.authenticate(token); // AuthenticationManager를 통해 인증을 시도한다
        // 세션에 인증 정보 저장
        /*
        SecurityContextHolder는 SpringSecurity의 인증 정보를 저장하고 조회하는 컨텍스트
        1. 인증이 성공하면 Authentication 객체를 SecurityContextHolder에 저장
        2. 이후 클라인언트의 요청은 사용자 인증상태를 유지하게끔 한다
        3. SPRING_SECURITY_CONTEXT는 SpringSecurity에서 사용하는 세션의 기본키이다
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
