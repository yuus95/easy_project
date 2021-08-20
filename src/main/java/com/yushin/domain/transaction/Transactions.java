package com.yushin.domain.transaction;


import com.yushin.domain.Address;
import com.yushin.domain.baseEntity;
import com.yushin.domain.member.Member;
import com.yushin.domain.memberAccount.MemberAccount;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Transactions extends baseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId",nullable = false)
    private Member member;

    //계좌번호
    @Column(nullable = false,length = 45)
    private String bankAccount;

    //금액
    @Column(nullable = false)
    private BigDecimal money;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;


    //이체,입금- 상대 계좌
    @Column(length = 45)
    private String counterparty_account;

    //이체,입금 - 상대이름
    @Column(length = 45)
    private String counterparty_name;

    // 결제 -사용처
    @Column(length = 45)
    private String payment_name;

    // 결제- 사용처 주소
    @Column(length = 45)
    private Address payment_address;

    // 결제 - 카드정보
    @Column(length = 45)
    private String payment_card;

    // 결제 - 결제처 전화번호
    @Column(length = 45)
    private String payment_phone;
    // 메모
    @Column(length = 1000)
    private String memo;


}
