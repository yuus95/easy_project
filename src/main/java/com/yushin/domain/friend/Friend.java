package com.yushin.domain.friend;


import com.yushin.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member uesr;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Member member;





}



