package com.travelnestpro.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.travelnestpro.controller..*(..)) || execution(* com.travelnestpro.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            logger.info("{} completed in {} ms", joinPoint.getSignature().toShortString(), System.currentTimeMillis() - start);
            return result;
        } catch (Throwable throwable) {
            logger.error("{} failed after {} ms: {}", joinPoint.getSignature().toShortString(), System.currentTimeMillis() - start, throwable.getMessage());
            throw throwable;
        }
    }
}
