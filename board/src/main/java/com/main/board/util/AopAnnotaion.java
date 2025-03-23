package com.main.board.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
1. @interface : 어노테이션을 만들때 사용하는 키워드
2. @Retention : 어노테이션의 범위를 지정하는 어노테이션(해당 어노테이션이 언제까지 살아있는가)
                @Retention(RetentionPolicy.SOURCE)
                ➜ 컴파일 전 소스 코드(.java)까지 남아있음
                @Retention(RetentionPolicy.CLASS)
                ➜ 컴파일 후 클래스 파일(.class)까지 남아있음 ( default )
                @Retention(RetentionPolicy.RUNTIME)
                ➜ 런타임까지 남아있음 ( 사실상 안사라짐 )
                ➜ JVM 환경에서 실제로 사용하기 위해서는 RUNTIME을 적용해주어야 함

3. @Target : 어노테이션이 적용될 대상을 지정하는 어노테이션
             @Target(ElementType.TYPE)
            ➜ 클래스, 인터페이스, enum
            @Target(ElementType.FIELD)
            ➜ 필드
            @Target(ElementType.METHOD)
            ➜ 메서드 ( 이 프로젝트에서 사용 )
            @Target(ElementType.PARAMETER)
            ➜ 파라미터
            @Target(ElementType.CONSTRUCTOR)
            ➜ 생성자
            @Target(ElementType.LOCAL_VARIABLE)
            ➜ 지역 변수
            @Target(ElementType.ANNOTATION_TYPE)
            ➜ 애너테이션
            @Target(ElementType.PACKAGE)
            ➜ 패키지
            @Target(ElementType.TYPE_USE)
            ➜ 타입
            @Target(ElementType.TYPE_PARAMETER)
            ➜ 타입 파라미터
            @Target(ElementType.MODULE)
            ➜ 모듈
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface AopAnnotaion {
}
