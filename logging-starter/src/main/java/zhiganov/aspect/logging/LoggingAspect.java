package zhiganov.aspect.logging;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
//import homework.zhiganov.timesheet.model.Timesheet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
//@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;
    private final Boolean printArgs; 

    @Pointcut("@annotation(zhiganov.aspect.logging.Logging)")
    public void loggingMethodsPointcut(){

    }
    @Pointcut("@within(zhiganov.aspect.logging.Logging)")
    public void loggingTypePointcut(){

    }
    @Around(value="loggingMethodsPointcut() || loggingTypePointcut()")//Pointcut - точка входа в аспект
    public Object loggingMethod(ProceedingJoinPoint pjp)throws Throwable{


        String methodName = pjp.getSignature().getName();
        String className = pjp.getClass().getName();
        printMessage("Before", className, methodName);
        try{
            return pjp.proceed();
        }
        finally{
            printMessage("After", className, methodName);
        }

    }

    private void printMessage(String prefix, String className, String methodName){

        if(printArgs){
            log.atLevel(properties.getLevel()).log("{}-> {}#{}",prefix, className, methodName);
        }
        
    }
}
