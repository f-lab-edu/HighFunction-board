package com.main.board.login.controller;

import com.main.board.login.DTO.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //JSON을 받기위한 어노테이션
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            /*
            인증 요청
            토큰이라고 해서 토큰방식을 사용하는것이 아니고
            Spring Security에서 토큰 기반의 인증을 수행하는 객체이다
            Spring Secutiry 내부 에서 사용자 인증 정보를 담기위한 객체이다
             */
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()); //토큰생성
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
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); //로그아웃시에 세션을 무효화하여 저장된 모든 데이터를 삭제한다 "SPRING_SECURITY_CONTEXT"해당 키 삭제
        SecurityContextHolder.clearContext(); //SecurityContextHolder의 인증정보를 삭제한다
        return ResponseEntity.ok("Logout successful");
    }
}
