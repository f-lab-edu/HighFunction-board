package com.main.board.member.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackages = "com.main.board.member.controller")
public class SignUpExceptionHandler  {

    //회원가입 실패
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.");
    }
}
