package com.yushin.handler.ex;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationErrorResponse {
    private int status;
    private String message;
}
