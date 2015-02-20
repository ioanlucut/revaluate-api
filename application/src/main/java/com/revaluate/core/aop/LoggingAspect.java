package com.revaluate.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/*@Aspect
@Component*/
public class LoggingAspect {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.revaluate..*(..))")
    protected void loggingOperation() {
    }

    @Before("loggingOperation()")
    @Order(1)
    public void logJoinPoint(JoinPoint joinPoint) {
        LOGGER.info("Join point kind : " + joinPoint.getKind());
        LOGGER.info("Signature declaring type : " + joinPoint.getSignature().getDeclaringTypeName());
        LOGGER.info("Signature name : " + joinPoint.getSignature().getName());
        LOGGER.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOGGER.info("Target class : " + joinPoint.getTarget().getClass().getName());
        LOGGER.info("This class : " + joinPoint.getThis().getClass().getName());
    }

    @AfterReturning(pointcut = "loggingOperation()", returning = "result")
    @Order(2)
    public void logAfter(JoinPoint joinPoint, Object result) {
        LOGGER.info("Exiting from Method :" + joinPoint.getSignature().getName());
        LOGGER.info("Return value :" + result);
    }

    @AfterThrowing(pointcut = "execution(* com.revaluate..*(..))", throwing = "e")
    @Order(3)
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.error("An exception has been thrown in " + joinPoint.getSignature().getName() + "()");
        LOGGER.error("Cause :" + e.getCause());
    }

    @Around("execution(* com.revaluate..*(..))")
    @Order(4)
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("The method " + joinPoint.getSignature().getName() + "() begins with " + Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            LOGGER.info("The method " + joinPoint.getSignature().getName() + "() ends with " + result);
            return result;
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }


}