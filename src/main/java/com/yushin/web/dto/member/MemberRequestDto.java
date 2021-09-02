package com.yushin.web.dto.member;

import com.yushin.domain.member.AuthProvider;
import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {


    @NotBlank(message = "닉네임은 필수 입니다.")
    private String nickname;


    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "핸드폰번호는 필수입니다.")
    @Pattern(regexp ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",message = "xxx-xxxx-xxxx 형식을 맞춰주세요")
    private String phone;

    @NotBlank(message = "생년월일은 필수입니다.")
    @Pattern(regexp ="^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$",message = "생년월일 0000-00-00 형식을 맞춰주세요")
    private String birthDay;




    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .birthDay(birthDay)
                .authority(Authority.ROLE_USER)
                .provider(AuthProvider.local)
                .build();
    }


}