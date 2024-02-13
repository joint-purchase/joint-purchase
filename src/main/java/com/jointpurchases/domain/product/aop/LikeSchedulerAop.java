package com.jointpurchases.domain.product.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LikeSchedulerAop {

    @Before("execution(* com.jointpurchases.domain.product.service.scheduler.LikeScheduler.likeCountDBUpdate(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Starting likeCountDBUpdate in LikeScheduler");
    }

    @After("execution(* com.jointpurchases.domain.product.service.scheduler.LikeScheduler.likeCountDBUpdate(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Completed likeCountDBUpdate in LikeScheduler");
    }


}