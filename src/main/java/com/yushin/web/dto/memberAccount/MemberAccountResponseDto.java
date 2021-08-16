package com.yushin.web.dto.memberAccount;

import com.yushin.domain.member.Member;
import com.yushin.domain.memberAccount.MemberAccount;
import com.yushin.web.dto.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberAccountResponseDto {

    private long id;
    private String bankName;
    private String bankAccount;


    public static MemberAccountResponseDto of(MemberAccount memberAccount) {
        return new MemberAccountResponseDto(memberAccount.getMember().getId(),memberAccount.getBankName(),memberAccount.getBankAccount());
    }
}
