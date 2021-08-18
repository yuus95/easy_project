package com.yushin.domain.transaction;

import com.yushin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction,Long> {


    //날짜 별 조회
    List<Transaction> findAllByMemberAndCreateDateBetween(Member member,LocalDateTime start,LocalDateTime end);

    //거래내역 전체 조회
    List<Transaction> findAllByMember(Member member);

    //계좌별 거래내역 조회
    List<Transaction> findAllByBankAccount(String bankAccount);

   //거래내역 유형별 조회
   List<Transaction> findAllByTransactionTypeAndMember(TransactionType transactionType,Member member);

}
