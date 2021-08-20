package com.yushin.service;


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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionsRepository;

    @Transactional
    public Transactions save(Transactions transactions){
        Transactions save = transactionsRepository.save(transactions);
        return save;
    }



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

    public Page<TransactionsDto> getTransaction(long id,int page,int size, String sort) {

        if (page < 0){
            throw  new CustomValidationException("페이지는 필수입니다.");
        }
        if (size == 0 ){
            throw  new CustomValidationException("size는 필수입니다.");
        }if (sort == "" || sort == null ){
            throw  new CustomValidationException("sort는 필수입니다.");
        }
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
}
