package com.yushin.domain.member;


import com.yushin.domain.baseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Entity
public class Member extends baseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 25,nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String password;

//    @Column(length = 25 ,nullable = false)
    @Column
    private String phone;

    @Column
    private String birthDay;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member update(String name){
        this.nickname=name;
        return this;
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;


    public String getKey(Authority authority){
        if (authority == Authority.ROLE_USER){
            return "ROLE_USER";
        }
        else{
            return "ROLE_ADMIN";
        }
    }

    @Builder
    public Member(String nickname, String email, String password, String phone, String birthDay,Authority authority,AuthProvider provider,String providerId){
        this.nickname =nickname;
        this.email=email;
        this.password = password;
        this.phone=phone;
        this.birthDay =birthDay;
        this.authority = authority;
        this.provider = provider;
        this.providerId = providerId;

    }

}
