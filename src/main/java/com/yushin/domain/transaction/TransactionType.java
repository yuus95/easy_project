package com.yushin.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;



/**
 * 이체 , 입금 , 결제 ?
 */
public enum TransactionType {

    payment, //결제
    deposit, //입금
    transfer //이체
}
