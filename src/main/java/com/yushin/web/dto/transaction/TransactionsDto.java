package com.yushin.web.dto.transaction;


import com.yushin.domain.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 거래내역 보여주는 API Dto
 *
 *  거래내역 번호,거래일시, 금액, 계좌, 거래 유형
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionsDto {


    private long id;

    private String bankAccount;

    private String money;

    private TransactionType transactionType;

    private LocalDateTime createDate;


}
