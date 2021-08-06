package com.yushin.domain.userAccount;


import com.yushin.domain.baseEntity;
import com.yushin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class UserAccount extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",nullable = false)
    private User user;


    @Column(length = 45,nullable = false)
    private String bankName;


    @Column(length = 45,nullable = false)
    private String bankAccount;

    @Builder
    public UserAccount(User user,String bankName,String bankAccount){
    this.user=user;
    this.bankName=bankName;
    this.bankAccount=bankAccount;
    }
}
