package com.yushin.handler.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CustomException extends  RuntimeException{
    private final ErrorCode errorCode;

}
