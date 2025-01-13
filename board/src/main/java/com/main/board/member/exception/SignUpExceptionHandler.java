package com.main.board.member.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackages = "com.main.board.member.controller")
public class SignUpExceptionHandler  {

    //회원가입 실패
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.");
    }

    // 이메일 중복 예외 처리
    @ExceptionHandler(EmailDuplicatedException.class)
    public ProblemDetail handleEmailDuplicatedException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
    }

    //파라마터가 제대로 전달되지않았을때
    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "파라미터가 제대로 전달되지 않았습니다.");
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
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "회원가입 유효성 검증 실패");
    }

}
