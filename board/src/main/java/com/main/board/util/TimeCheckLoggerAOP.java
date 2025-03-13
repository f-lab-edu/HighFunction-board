package com.main.board.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect // AOP를 사용하기 위한 어노테이션 (AOP를 담당한다)
@Component // 빈등록
public class TimeCheckLoggerAOP {


    //@Around("execution(* com.main.board.*.controller.*.*(..))") // AOP를 적용할 범위를 지정
    @Around("@annotation(com.main.board.util.AopAnnotaion)") // 어노테이션 사용시 이렇게 사용
    public Object timeCheckLoggerAOP(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        log.info("{} 실행시간 : {}ms", joinPoint.getSignature().toShortString(), stopWatch.getTotalTime(TimeUnit.MILLISECONDS));
        return result;
    }
}
