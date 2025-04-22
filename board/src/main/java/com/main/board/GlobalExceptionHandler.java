package com.main.board;

import com.main.board.member.exception.EmailDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //서버 500 에러
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception e) {
        log.error("에러 로그", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러입니다 다시시도해주세요.");
    }

    // 이메일 중복 에러
    @ExceptionHandler(EmailDuplicatedException.class)
    public ProblemDetail handleEmailDuplicatedException(Exception e) {
        log.error("에러 로그", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
    }

    //nullpoint 에러 (파라미터 누락)
    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "파라미터가 제대로 전달되지 않았습니다.");
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        if(e.getMessage() != null) {
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "파라미터가 제대로 전달되지 않았습니다.");
    }

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
