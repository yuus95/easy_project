package com.yushin.handler;


import com.yushin.handler.ex.CustomException;
import com.yushin.handler.ex.CustomValidationException;
import com.yushin.handler.ex.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


/**
 * 컨트롤에 익셉션을 다 낚아챈다.
 * 나중에사용하기
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    
    
    /**
     * 유효성 에러 잡기
     */
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> CustomValidationException(CustomValidationException e){
        log.error("handleCustomValidationException throw CustomException: {}", e.getMessage());

        return ErrorResponse.toValidationResponseEntity(e.getMessage());
    }
    
    /**
     * API에러 잡기
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("handleCustomException throw CustomException: {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
