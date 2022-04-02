package com.humga.moneytransferservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    //логер
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    //логируем все методы контроллеров пакета controller
    @Pointcut("within(com.humga.moneytransferservice.controller..*)")
    private void everythingInMyApplication() {}

    //Перед выполнением метода логируем аргументы, то есть входной DTO из реквеста
    @Before("com.humga.moneytransferservice.aspect.LogAspect.everythingInMyApplication()")
    public void logMethodName(JoinPoint joinPoint) {
        LOG.info("Called {}", joinPoint.getSignature().getName() +
                ": " + Arrays.stream(joinPoint.getArgs()).reduce((a,b) -> a+b.toString()));
    }

    //После возврата из метода логируем возвращенный DTO
    @AfterReturning(pointcut="com.humga.moneytransferservice.aspect.LogAspect.everythingInMyApplication()",
            returning="retVal")
    public void logReturn(JoinPoint joinPoint, Object retVal) {
        LOG.info("Returned {}", joinPoint.getSignature().getName() +
                ": " + retVal.toString());
    }
}