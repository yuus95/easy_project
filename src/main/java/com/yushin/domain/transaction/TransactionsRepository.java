package com.yushin.domain.transaction;

import com.yushin.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions,Long>, TransactionsRepositoryCustom {

    /**
     * 0819 - 잔액확인하기 ,  Querydsl 동적쿼리 적용하기(거래내역 type & 날짜별 조회)
     */

    // 잔액확인하기 입금 - (결제,이체)

    // 동적쿼리 작성하기


    //날짜 별 조회
    List<Transactions> findAllByMemberAndCreateDateBetween(Member member, LocalDateTime start, LocalDateTime end);

    //거래내역 전체 조회
    List<Transactions> findAllByMember(Member member);


   //거래내역 유형별 조회
   List<Transactions> findAllByTransactionTypeAndMember(TransactionType transactionType, Member member);

    @Query("select sum(t.money) from Transactions t where t.transactionType ='deposit' ")
    BigDecimal findByDeposit(Member member);

    @Query("select sum(t.money) from Transactions t where t.transactionType = 'transfer' or t.transactionType = 'payment' ")
    BigDecimal findByTransferAndPayment(Member member);


    @Query(value = "select t  from Transactions t where t.member.id = :memberId",countQuery = "select count(t.id) from Transactions t where t.member.id = :memberId")
    Page<Transactions> findPageByMemberId(@Param("memberId") long memberId, Pageable pageable);

    @Transactional
    void deleteByBankAccount(String bankAccount);
}
