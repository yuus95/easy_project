package com.yushin.domain.fixedMoney;


import com.yushin.domain.baseEntity;
import com.yushin.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class FixedMoney extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;


    @Column(nullable = false)
    private String type;

    private long fee;

    @Column(nullable = false)
    private BigDecimal money;

    private String memo;



}


