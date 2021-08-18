package com.yushin.service;

import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.domain.memberAccount.MemberAccount;
import com.yushin.domain.memberAccount.MemberAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberAccountServiceTest {

    @Autowired
    MemberAccountRepository memberAccountRepository;
    
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void countFun() throws Exception{
        //given
        Member member = memberRepository.save(Member.builder()
                .nickname("test")
                .email("kkad45@naver.com")
                .password("1234")
                .phone("01047105883")
                .birthDay("1995-11-18")
                .authority(Authority.ROLE_USER)
                .build());
        
        MemberAccount save = memberAccountRepository.save(MemberAccount.builder()
        .member(member)
        .bankName("우리은행")
        .bankAccount("1560010001")
        .build());
        
        //when
        System.out.println("count-->" + memberAccountRepository.countByMember(member));
        System.out.println("FindByMemberAndAccount -  "+ memberAccountRepository.findByMemberAndBankAccount(member, save.getBankAccount()));
        //then
    }



}