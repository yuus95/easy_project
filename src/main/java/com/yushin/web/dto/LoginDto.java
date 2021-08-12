package com.yushin.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {


    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
