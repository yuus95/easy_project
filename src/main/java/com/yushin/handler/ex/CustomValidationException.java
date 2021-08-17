package com.yushin.handler.ex;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomValidationException extends RuntimeException {
    private final String message;
}
