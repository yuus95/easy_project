package com.yushin.web.dto;

import com.yushin.domain.user.Authority;
import com.yushin.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {


    @NotBlank
    private String ninckname;

    @Max(20)
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotBlank
    private String birthDay;



    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickname(ninckname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .birthDay(birthDay)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}