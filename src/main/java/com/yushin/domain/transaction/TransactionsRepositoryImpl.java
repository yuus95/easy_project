package com.yushin.domain.transaction;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yushin.domain.member.Member;
import com.yushin.web.dto.transaction.BalanceDto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.yushin.domain.transaction.QTransactions.transactions;

public class TransactionsRepositoryImpl implements TransactionsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    public TransactionsRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    QTransactions transactionSub = new QTransactions("transactionSub");


    @Override
    public List<Transactions> search(Member member, TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory
                .select(transactions)
                .from(transactions)
                .where((transactions.member.eq(member))
                        ,typeEq(type)
                ,transactions.createDate.between(startDate,endDate))
                .fetch();

    }

    @Override
    public Optional<BalanceDto> getBalance(long id) {
         Tuple result =  jpaQueryFactory
                .select(
                        ExpressionUtils.as(
                                JPAExpressions
                                .select(transactionSub.money.sum())
                                .from(transactionSub)
                                .where(transactionSub.transactionType.eq(TransactionType.deposit))
                        ,"deposit")
                        ,ExpressionUtils.as(
                                JPAExpressions
                                .select(transactionSub.money.sum())
                                .from(transactionSub)
                                .where(transactionSub.transactionType.eq(TransactionType.transfer).or(transactionSub.transactionType.eq(TransactionType.payment)))
                        ,"minius_money")
                )
                .from(transactions)
                .where(transactions.member.id.eq(id))
                .fetchFirst();
        if (result== null) {
            return null;
        }
         BalanceDto balanceDto = new BalanceDto(result.get(0,BigDecimal.class),result.get(1,BigDecimal.class));
        Optional<BalanceDto>  getResult = Optional.ofNullable(balanceDto);
        return getResult;
    }


    private BooleanExpression typeEq(TransactionType type) {
        return type != null ? transactions.transactionType.eq(type) : null;
    }
}
