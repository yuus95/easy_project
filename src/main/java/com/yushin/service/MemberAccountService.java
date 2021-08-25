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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yushin.handler.ex.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberAccountService {
    private final MemberAccountRepository memberAccountRepository;
    private final MemberRepository memberRepository;


    /**
     * 계좌 등록
     */
    @Transactional
    public void register(MemberAccountRequestDto  memberAccountRequestDto){
        Optional<Member> byId = memberRepository.findById(memberAccountRequestDto.getMemberId());
        if (memberAccountRepository.existsByBankAccountAndMember(memberAccountRequestDto.getBankAccount(),byId.get())){
            throw new CustomException(DUPLICATE_ACCOUNT);
        }

        if (memberAccountRepository.countByMember(byId.get()) == 5){
            throw new CustomException(ACCOUNT_OVER);
        }

        MemberAccount save = memberAccountRepository.save(memberAccountRequestDto.toMemberAccount(byId.get()));
    }

    /**
     * 계좌한개 조회
     */
    @Transactional(readOnly = true)
    public MemberAccountResponseDto getOneAccount(long id, String account){
        Optional<MemberAccount> byMemberIdandBankAccount =memberAccountRepository.findByMemberIdandBankAccount(id, account);
        if (byMemberIdandBankAccount.isEmpty()){
            throw new CustomException(CANNOT_FIND_ACCOUNT);
        }

       return MemberAccountResponseDto.of(byMemberIdandBankAccount.get());
    }
    /**
     * 유저 계좌 모두 조회
     */
    @Transactional(readOnly = true)
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
    
    /**
     * 계좌 조회
     */
    @Transactional
    public void DeleteAccount(long id,String bankAccount){
        Optional<Member> byId = memberRepository.findById(id);

         memberAccountRepository.existsByMemberAndBankAccount(byId.get(), bankAccount);
        if (!memberAccountRepository.existsByMemberAndBankAccount(byId.get(), bankAccount)){
            throw new CustomException(CANNOT_FIND_ACCOUNT);
        }
        memberAccountRepository.deleteByBankAccount(bankAccount);
    }
}
