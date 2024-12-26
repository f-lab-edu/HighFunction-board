package com.main.board.login.controller;

import com.main.board.login.DTO.LoginRequest;
import com.main.board.login.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //JSON을 받기위한 어노테이션
@RequestMapping("/auth")
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        loginService.login(loginRequest);
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); //로그아웃시에 세션을 무효화하여 저장된 모든 데이터를 삭제한다 "SPRING_SECURITY_CONTEXT"해당 키 삭제
        SecurityContextHolder.clearContext(); //SecurityContextHolder의 인증정보를 삭제한다
        return ResponseEntity.ok().build(); // build()는 바디없이 빈 응답을 생성한다
    }
}
