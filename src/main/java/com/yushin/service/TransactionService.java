package com.yushin.service;


import com.querydsl.core.Tuple;
import com.yushin.domain.transaction.TransactionType;
import com.yushin.domain.transaction.Transactions;
import com.yushin.domain.transaction.TransactionsRepository;
import com.yushin.handler.ex.CustomValidationException;
import com.yushin.web.dto.transaction.BalanceDto;
import com.yushin.web.dto.transaction.TransactionsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionsRepository;

    @Transactional
    public Transactions save(Transactions transactions){
        Transactions save = transactionsRepository.save(transactions);
        return save;
    }



    /**
     * 잔액 보여주는 api
     */
    @Transactional(readOnly = true)
    public String getBalnce(long id) {
        Optional<BalanceDto> balanceDto = transactionsRepository.getBalance(id);
        if (balanceDto.isEmpty()){
            return  "0원";
        }

        BigDecimal deposit = balanceDto.get().getDeposit();
        BigDecimal minusMoney = balanceDto.get().getMinus_money();

        if (deposit == null){
            deposit =new BigDecimal("0");
        }

        if (minusMoney == null){
            minusMoney = new BigDecimal("0");
        }

        BigDecimal balance = deposit.subtract(minusMoney);
        balance = balance.setScale(0);

        DecimalFormat df = new DecimalFormat("###,###");
        String money = df.format(balance);

        return  money+"원";
    }
    
    
    /**
     * 동적쿼리 사용전 거래유형 가져오는 함수
     */
    public Page<TransactionsDto> getTransaction(long id,int page,int size, String sort) {
        DecimalFormat df = new DecimalFormat("###,###");

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Transactions> pageByMemberId = transactionsRepository.findPageByMemberId(id, pageRequest);
        System.out.println("pageBymemberId .content  " + pageByMemberId.getContent() );
        Page<TransactionsDto> dtoPage = pageByMemberId.map(
                transactions -> TransactionsDto.builder()
                        .id(transactions.getId())
                        .bankAccount(transactions.getBankAccount())
                        .transactionType(transactions.getTransactionType())
                        .createDate(transactions.getCreateDate())
                        .money(df.format(transactions.getMoney())+"원")
                        .build()
        );

        return dtoPage;
    }
    
    
    /**
     * 동적쿼리  계좌 거래내역 조회
     * account,type 넣어도 되고 안넣어됨
     *
     */
    @Transactional(readOnly = true)
    public List<TransactionsDto> getTransactionType(long id,String account,String type,int startPage,int endPage,String startDate, String endDate) {


        LocalDateTime stDate = LocalDateTime.of(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE), LocalTime.of(0,0,0));
        LocalDateTime edDate = LocalDateTime.of(LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE), LocalTime.of(23,59,59));
        TransactionType ttType = null;

        if (type != null){
            if (type.equals("transfer")){
                ttType = TransactionType.transfer;
            }
            else if(type.equals("deposit")){
                ttType = TransactionType.deposit;
            }
            else if(type.equals("payment")){
                ttType = TransactionType.payment;
            }

        }

    
        List<TransactionsDto> collect = transactionsRepository.search(id, account, ttType, startPage, endPage, stDate, edDate)
                .stream().map(o -> TransactionsDto.of(o))
                .collect(Collectors.toList());

        return collect;

    }
}
