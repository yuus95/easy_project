package com.yushin.domain.memberAccount;


import com.yushin.domain.baseEntity;
import com.yushin.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MemberAccount extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId",nullable = false)
    private Member member;


    @Column(length = 45,nullable = false)
    private String bankName;


    @Column(length = 45,nullable = false)
    private String bankAccount;

    @Builder
    public MemberAccount(Member member, String bankName, String bankAccount){
    this.member =member;
    this.bankName=bankName;
    this.bankAccount=bankAccount;
    }
}
