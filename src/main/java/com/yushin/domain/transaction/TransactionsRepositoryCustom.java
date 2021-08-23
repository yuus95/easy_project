package com.yushin.domain.transaction;

import com.querydsl.core.Tuple;
import com.yushin.domain.member.Member;
import com.yushin.web.dto.transaction.BalanceDto;
import com.yushin.web.dto.transaction.TransactionsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionsRepositoryCustom {

    List<Transactions> search(long memberId , String account, TransactionType type, int startPage , int endPage, LocalDateTime startDate, LocalDateTime endDate);
    Optional<BalanceDto> getBalance(long id);

}
