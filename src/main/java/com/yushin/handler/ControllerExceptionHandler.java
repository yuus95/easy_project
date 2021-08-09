package com.yushin.handler;


import com.yushin.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 컨트롤에 익셉션을 다 낚아챈다.
 */
@RestController
@ControllerAdvice
public class ControllerExceptionHandler {


    /**
     * runtimeException이 발생하는 모든
     */
    @ExceptionHandler(CustomValidationException.class)
    public Map<String,String> validationException(CustomValidationException e){
        return e.getErrorMap();
    }

}
