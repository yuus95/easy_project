package com.yushin.web.dto.member;

import com.yushin.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(Member member){
        this.name = member.getNickname();
        this.email = member.getEmail();
    }
}