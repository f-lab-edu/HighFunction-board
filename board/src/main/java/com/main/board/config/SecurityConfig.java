package com.main.board.config;

import com.main.board.util.Bcrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration // 스프링부트에게 이 클래스가 설정파일임을 알려줌 (빈등록)
@EnableWebSecurity // Spring Security 활성화 기본보안 필터체인이 적용된다
public class SecurityConfig {

    //로그인은 당연히 폼으로 사용할것같다는 가정 하에 설정 (JSON이있을라나?)
    // TODO http://localhost:8081/member/signup 회원가입 테스트시 포스트맨에서 시큐리티 기본 폼화면이 리턴됨
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable()) // csrf 비활성화 (REST API등 비상태 통신에서는 CSRF토큰이 필요하지 않을수있다)
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers("/", "/member/signup").permitAll() // "/" 경로는 모든 사용자에게 허용
                                .anyRequest().authenticated() // 그 외의 경로는 인증된 사용자에게만 허용
                )
                .formLogin(form -> form
                .loginProcessingUrl("/login") // 로그인 처리 URL
                .usernameParameter("userId") //폼에서 받을 아이디 파라미터
                .passwordParameter("password") // 폼에서 받을 비밀번호 파라미터
                .failureHandler(loginFailHandler())
                .successHandler(loginSuccessHandler())
                .permitAll() // 로그인 페이지는 모든 사용자에게 허용
                );
        return http.build();
    }

    //해당 방식으로 스프링 시큐리티가 CustomUserDetails 객체에서 반환된 비밀번호와 로그인 요청에서 받은 비밀번호를 비교.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Bcrypt();
    }

    //인스턴스 생성
    @Bean
    public AuthenticationFailureHandler loginFailHandler() {
        return new LoginFailHandler();
    }

    //인스턴스 생성
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
