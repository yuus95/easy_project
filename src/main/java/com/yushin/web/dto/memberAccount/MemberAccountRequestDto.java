package com.yushin.web.dto.memberAccount;


import com.yushin.domain.member.Member;
import com.yushin.domain.memberAccount.MemberAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountRequestDto {

    @NotNull(message = "멤버아이디값은 필수입니다.")
    private Long memberId;
    @NotBlank(message = "은행이름은 필수입니다.")
    private String bankName;

    @NotBlank(message = "계좌번호는 필수입니다.")
//    @Pattern(regexp = "[0-9,\\-]{3,6}\\-[0-9,\\-]{2,6}\\-[0-9,\\-]",message = "계좌번호를 제대로 입력해주세요.")
    private String bankAccount;


    public MemberAccount toMemberAccount(Member member){
        return MemberAccount.builder()
                .member(member)
                .bankName(bankName)
                .bankAccount(bankAccount)
                .build();
    }
}
