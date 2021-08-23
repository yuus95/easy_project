package com.yushin.web.dto.transaction;


import com.yushin.domain.transaction.TransactionType;
import com.yushin.domain.transaction.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    private static DecimalFormat df = new DecimalFormat("###,###");

    private long id;

    private String bankAccount;

    private String money;

    private TransactionType transactionType;

    private LocalDateTime createDate;




    public static TransactionsDto of(Transactions transactions){
        return new TransactionsDto(
                transactions.getId(),
                transactions.getBankAccount(),
                df.format(transactions.getMoney())+"원",
                transactions.getTransactionType(),
                transactions.getCreateDate()
                );
    }
}
