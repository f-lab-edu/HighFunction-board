package com.main.board.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
  @RestControllerAdvice 와 @ControllerAdvice 는 예외처리를 전역적으로 처리하기 위한 어노테이션 (spring 3.2부터 지원 Rest는 4.3부터 지원)
  두개의 차이는 Rest즉 @ResponseBody가 유무 차이로 응답이 Json으로 내려주는지 아닌지의 차이이다
  @ResponseBody는 메소드의 반환값을 Http 응답 바디에 직접 넣어주겠다는 의미이다
 */
@RestControllerAdvice(basePackages = "com.main.board.login.controller")
public class LoginExceptionHandler {

    /*
    @ExceptionHandler 어노테이션은 특정 예외가 발생했을 때 메소드가 처리하도록 하는 어노테이션이다
    여기서는 BadCredentialsException 예외가 발생했을 때 handleBadCredentialsException 메소드가 처리하도록 한다
    ProblemDetail은 Spring(6부터)에서 제공하는 클래스로 예외처리시 상태코드와 상세정보를 담아서 반환할 수 있다
    기존방식으로 처리하게되면 ResponseEntity<Map<String, Object>> 형식으로 직접 관리를 해야하지만 ProblemDetail을 사용하면 편리하게 처리할 수 있다
     */

    //로그인 실패 (비밀번호 불일치) 예외처리
    // UsernameNotFoundException은 필요없다 (BadCredentialsException로 spring security에서 처리)
    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "아이디or비밀번호가 일치하지 않습니다");
    }

    //유효성 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 첫 번째 오류 메시지 추출
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0); // 첫 번째 오류만 처리
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();

        // 이메일 오류
        if ("email".equals(field)) {
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, defaultMessage);
        }

        // 비밀번호 오류
        if ("rawPassword".equals(field)) {
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, defaultMessage);
        }
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "유효성 검증 실패");
    }


}
