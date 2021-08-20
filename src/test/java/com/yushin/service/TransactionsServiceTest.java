package com.yushin.service;

import com.yushin.domain.Address;
import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.domain.transaction.Transactions;
import com.yushin.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionsServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void 데이터생성(){

        Address address = new Address("경기도","소하1동","1955");
        Member member = memberRepository.save(
                Member.builder()
                        .email("kkad45@naver.com")
                        .password("1234")
                        .phone("01047105883")
                        .nickname("바쟈")
                        .birthDay("19951119")
                        .authority(Authority.ROLE_USER)
                        .build());

        Transactions 김유신 = transactionService.save(Transactions.builder()
                .bankAccount("7560010003226")
                .transactionType(TransactionType.deposit)
                .money(new BigDecimal("100000"))
                .counterparty_name("김유신")
                .counterparty_account("01047105883")
                .member(member)
                .build());


        Transactions 김유신1 = transactionService.save(Transactions.builder()
                .bankAccount("7560010003226")
                .transactionType(TransactionType.deposit) // 입금
                .money(new BigDecimal("10000"))
                .counterparty_name("김유신")
                .counterparty_account("")
                .member(member)
                .build());

        Transactions 김유신2 = transactionService.save(Transactions.builder()
                .bankAccount("7560010003226")
                .transactionType(TransactionType.deposit)
                .money(new BigDecimal("10000"))
                .counterparty_name("김유신")
                .counterparty_account("")
                .member(member)
                .build());


        Transactions 박흥 = transactionService.save(Transactions.builder()
                .bankAccount("7560010003226")
                .transactionType(TransactionType.transfer) // 이체
                .money(new BigDecimal("6000"))
                .counterparty_name("박흥")
                .counterparty_account("010666622222")
                .member(member)
                .build());

        Transactions 고구려짬뽕 = transactionService.save(Transactions.builder()
                .bankAccount("7560010003226")
                .transactionType(TransactionType.payment) //결제
                .money(new BigDecimal("30000"))
                .payment_address(address)
                .payment_name("고구려짬뽕")
                .payment_card("0788")
                .payment_phone("010-4710-5883")
                .member(member)
                .build());
    }

    @Test
    public void getBalanceTest() throws Exception{
        //given
        System.out.println("시작가능?");
        //when

        //then
    }





}