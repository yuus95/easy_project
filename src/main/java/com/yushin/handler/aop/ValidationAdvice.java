package com.yushin.handler.aop;


import com.yushin.handler.ex.ValidationErrorResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.yushin.web.controller.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    ValidationErrorResponse er = new ValidationErrorResponse(400,bindingResult.getAllErrors().get(0).getDefaultMessage());
                    return new ResponseEntity<ValidationErrorResponse>(er,HttpStatus.BAD_REQUEST);
                }

            }
        }
        return proceedingJoinPoint.proceed();
    }
}
