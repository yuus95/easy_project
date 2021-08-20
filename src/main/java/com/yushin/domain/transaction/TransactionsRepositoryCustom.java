package com.yushin.domain.transaction;

import com.yushin.domain.member.Member;
import com.yushin.web.dto.transaction.BalanceDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionsRepositoryCustom {

    List<Transactions> search(Member member , TransactionType type, LocalDateTime startDate, LocalDateTime endDate);
    Optional<BalanceDto> getBalance(long id);

}
