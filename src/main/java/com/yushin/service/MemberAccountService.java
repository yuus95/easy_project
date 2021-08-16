package com.yushin.service;


import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.domain.memberAccount.MemberAccount;
import com.yushin.domain.memberAccount.MemberAccountRepository;
import com.yushin.handler.ex.CustomException;
import com.yushin.handler.ex.ErrorCode;
import com.yushin.web.dto.memberAccount.MemberAccountRequestDto;
import com.yushin.web.dto.memberAccount.MemberAccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberAccountService {
    private final MemberAccountRepository memberAccountRepository;
    private final MemberRepository memberRepository;

    public void register(MemberAccountRequestDto  memberAccountRequestDto,long id){
        Optional<Member> byId = memberRepository.findById(id);
        MemberAccount save = memberAccountRepository.save(memberAccountRequestDto.toMemberAccount(byId.get()));
    }

    public MemberAccountResponseDto getOneAccount(long id, String account){


        Optional<MemberAccount> byMemberIdandBankAccount =memberAccountRepository.findByMemberIdandBankAccount(id, account);
        if (byMemberIdandBankAccount.isEmpty()){
            throw new CustomException(ErrorCode.CANNOT_FIND_ACCOUNT);
        }

       return MemberAccountResponseDto.of(byMemberIdandBankAccount.get());
    }

    public List<MemberAccountResponseDto> AllAccount(long id){


        List<MemberAccount> allByMemberId = memberAccountRepository.findAllByMemberId(id);
        if (allByMemberId.size() == 0){
            return null;
        }

        List<MemberAccountResponseDto> maDtoList = allByMemberId.stream()
                .map(o -> MemberAccountResponseDto.of(o))
                .collect(Collectors.toList());
        return maDtoList;
    }


}
