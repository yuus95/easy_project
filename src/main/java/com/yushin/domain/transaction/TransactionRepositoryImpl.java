package com.yushin.domain.transaction;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yushin.domain.member.Member;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.yushin.domain.transaction.QTransaction.transaction;

public class TransactionRepositoryImpl  implements TransactionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    public TransactionRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    QTransaction transactionSub = new QTransaction("transactionSub");


    @Override
    public List<Transaction> search(Member member,TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory
                .select(transaction)
                .from(transaction)
                .where((transaction.member.eq(member))
                        ,typeEq(type)
                ,transaction.createDate.between(startDate,endDate))
                .fetch();

    }


    private BooleanExpression typeEq(TransactionType type) {
        System.out.println("========= type = ? " + type);
        return type != null ? transaction.transactionType.eq(type) : null;
    }
}
