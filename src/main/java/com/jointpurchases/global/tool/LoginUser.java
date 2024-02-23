package com.jointpurchases.global.tool;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@SuppressWarnings("ALL")
@Target(ElementType.PARAMETER) // 메소드 파라미터에 사용됨을 명시
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 어노테이션 정보 유지
public @interface LoginUser {
}