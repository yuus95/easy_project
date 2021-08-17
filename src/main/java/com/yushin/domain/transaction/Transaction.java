package com.yushin.domain.transaction;


import com.yushin.domain.member.Member;
import com.yushin.domain.memberAccount.MemberAccount;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private MemberAccount memberAccount;



}
