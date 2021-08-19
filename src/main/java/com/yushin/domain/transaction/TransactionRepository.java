package com.yushin.domain.transaction;

import com.yushin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction,Long>,TransactionRepositoryCustom  {

    /**
     * 0819 - 잔액확인하기 ,  Querydsl 동적쿼리 적용하기(거래내역 type & 날짜별 조회)
     */

    // 잔액확인하기 입금 - (결제,이체)

    // 동적쿼리 작성하기


    //날짜 별 조회
    List<Transaction> findAllByMemberAndCreateDateBetween(Member member,LocalDateTime start,LocalDateTime end);

    //거래내역 전체 조회
    List<Transaction> findAllByMember(Member member);

    //계좌별 거래내역 조회
    List<Transaction> findAllByBankAccount(String bankAccount);

   //거래내역 유형별 조회
   List<Transaction> findAllByTransactionTypeAndMember(TransactionType transactionType,Member member);

   @Query("select sum(t.money) from Transaction t where t.transactionType ='deposit' ")
   BigDecimal findByDeposit(Member member);

    @Query("select sum(t.money) from Transaction t where t.transactionType = 'transfer' or t.transactionType = 'payment' ")
    BigDecimal findByTransferAndPayment(Member member);


}
