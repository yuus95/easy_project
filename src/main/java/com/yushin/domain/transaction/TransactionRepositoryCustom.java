package com.yushin.domain.transaction;

import com.yushin.domain.member.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepositoryCustom {

    List<Transaction> search(Member member ,TransactionType type, LocalDateTime startDate, LocalDateTime endDate);


}
