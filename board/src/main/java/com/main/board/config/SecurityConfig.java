package com.main.board.config;

import com.main.board.util.PassWordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링부트에게 이 클래스가 설정파일임을 알려줌 (빈등록)
@EnableWebSecurity // Spring Security 활성화 기본보안 필터체인이 적용된다
public class SecurityConfig {

    private UserDetailService userDetailsService;

    public SecurityConfig(UserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //final HttpSecurity http 이점이있는가?
        http
                .csrf((auth) -> auth.disable()) // csrf 비활성화 (REST API등 비상태 통신에서는 CSRF토큰이 필요하지 않을수있다)
                .httpBasic((auth) -> auth.disable()) // httpBasic 비활성화
                .formLogin((auth) -> auth.disable()) // formLogin 비활성화
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers("/", "/member/signup", "/auth/login").permitAll() // "/" 경로는 모든 사용자에게 허용
                .anyRequest().authenticated())
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //세션정책설정 (인증이 필요할때만 생성)
                /*
                **세션 고정 공격(Session Fixation Attack)**을 방지하기 위한 설정입니다.
                    세션 고정 공격은 공격자가 사용자의 세션 ID를 미리 설정하거나 가로채어 악용하는 공격입니다.

                    Spring Security에서는 세션 고정 공격을 방지하기 위해 새로운 세션을 생성하는 방식을 제공합니다.
                    newSession 설정은 인증 후 항상 새로운 세션을 생성합니다.
                    사용자가 로그인하거나 인증할 때 기존 세션을 폐기하고 새로운 세션을 생성합니다.
                    이로 인해 기존 세션 ID가 무효화되며, 세션 고정 공격을 방지합니다.
                */
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)//세션고정공격방지
                .maximumSessions(1) // 동시세션수 제한 (하나의 사용자계정이 유지할수있는 세션의 수를 제한)
                );

        return http.build();
    }

    // 로그인시에 Spring Security의 인증 진입점이다 클라이언트가 제공한 인증정보를 받아 AuthenticationManager를 통해 인증을 위임한다, Bean설정 필수
    // LoginController에서 사용
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     실제인증을 처리하는 부분인 Provider이다
        DaoAuthenticationProvider는 UserDetailsService를 통해 사용자 정보를 가져오고 비밀번호를 확인한다

     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService); // 사용자 정보를 로드할 서비스
        provider.setPasswordEncoder(passwordEncoder()); // 비밀번호 암호화 확인

        return provider;
    }


    //해당 방식으로 스프링 시큐리티가 CustomUserDetails 객체에서 반환된 비밀번호와 로그인 요청에서 받은 비밀번호를 비교.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PassWordEncoder();
    }

}
