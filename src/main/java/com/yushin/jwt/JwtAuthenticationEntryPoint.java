package com.yushin.jwt;


import com.yushin.handler.ex.CustomException;
import com.yushin.handler.ex.ErrorCode;
import com.yushin.handler.ex.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yushin.handler.ex.ErrorCode.DUPLICATE_MEMBER;

/**
 * https://sas-study.tistory.com/362?category=784778 참고하기
 * 유효한 자격증명을 제공하지 않고 접근하려할 떄 401 Unauthorized 에러를 리턴할 클래스
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        log.error("UnAuthorizaed!!! message : " + authException.getMessage());
        throw new CustomException(ErrorCode.NOT_MATCHED_PASSWORD);
    }

}
