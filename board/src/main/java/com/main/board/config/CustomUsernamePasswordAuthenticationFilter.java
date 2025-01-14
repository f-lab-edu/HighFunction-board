package com.main.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.board.login.DTO.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomUsernamePasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private static final String LOGIN_URL = "/auth/login";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setFilterProcessesUrl(LOGIN_URL);
        setSecurityContextRepository(new HttpSessionSecurityContextRepository());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            String email = loginRequest.getEmail();
            String password = loginRequest.getRawPassword();

            //@valid 어노테이션 사용불가 validation을 직접 구현하기엔좀 단점같음
            //직접구현
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("이메일을 입력해주세요.");
            }
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("비밀번호를 입력해주세요.");
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("로그인 성공!"); // 로그인 성공
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException failed) throws java.io.IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("로그인 실패!" + failed.getMessage()); // 로그인 실패
    }


}
