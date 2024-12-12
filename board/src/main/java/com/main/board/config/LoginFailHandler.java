package com.main.board.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 기본적으로 시큐리티에서 로그인 실패시에는 SimpleUrlAuthenticationFailureHandler를 사용한다.
 사용자가 인증에 실패하면 AuthenticationException이 발생
 SimpleUrlAuthenticationFailureHandler는 기본적으로 실패 후 로그인 페이지로 리다이렉트하며, URL에 ?error를 추가
 */

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        //로그인 실패로그
        System.out.println("로그인 실패: " + exception.getMessage());
        // 실패 응답 커스터마이즈
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401 Unauthorized
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"Login failed\", \"message\": \"" + exception.getMessage() + "\"}");
    }
}
