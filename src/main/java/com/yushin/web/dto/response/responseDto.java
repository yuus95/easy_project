package com.yushin.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class responseDto<T> {
    private int code;
    private String message;
    private T data;
}
