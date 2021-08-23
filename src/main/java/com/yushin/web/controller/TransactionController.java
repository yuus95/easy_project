package com.yushin.web.controller;


import com.yushin.handler.ex.CustomValidationException;
import com.yushin.service.TransactionService;
import com.yushin.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    /**
     * 잔액 확인 api
     */
    @GetMapping("/api/get-balance/{id}")
    public ResponseEntity<?> getBalance(@PathVariable long id){

        return new ResponseEntity<>(new ResponseDto<>(200,"잔액 조회를 성공했습니다.",transactionService.getBalnce(id)), HttpStatus.OK);
    }
    /**
     * 페이징 처리된 거래내역 
     * 멤버가 갖고 있는 계좌에 대한 모든 거래내역 조회
     */
    @GetMapping("/api/transaction/")
    public ResponseEntity<?> getTransaction(@RequestParam("id") long id ,@RequestParam("page")int page,@RequestParam("size") int size, @RequestParam("sort")String sort ){
        if (page < 0){
            throw  new CustomValidationException("페이지는 필수입니다.");
        }
        if (size == 0 ){
            throw  new CustomValidationException("size는 필수입니다.");
        }if (sort == "" || sort == null ){
            throw  new CustomValidationException("sort는 필수입니다.");
        }
        return new ResponseEntity<>(new ResponseDto<>(200,"거래내역 조회 성공",transactionService.getTransaction(id,page,size,sort)), HttpStatus.OK);
    }

    /**
     *  페이징처리된 거래유형별, 계좌별,날짜별 조회하는 동적쿼리 api
     *  /api/transaction-type?id=?&type=?&StartDate=?&EndDate=?
     *
     *  Response - > TransactonDto
     */
    @GetMapping("/api/transasction-type")
    public ResponseEntity<?> getTransactionType(@RequestParam("id") long id,@RequestParam(value = "account",required = false) String account
            ,@RequestParam(value = "type",required = false)String type,@RequestParam("startPage")int startPage,@RequestParam("endPage")int endPage
    ,@RequestParam("startDate")String startDate,@RequestParam("endDate") String endDate){

        if (startPage < 0){
            throw  new CustomValidationException("시작페이지는 필수입니다.");
        }
        if (endPage < 0  ){
            throw  new CustomValidationException("endPage는 필수입니다.");
        }
        if (startDate == null){
            throw  new CustomValidationException("startDate는 필수입니다.");
        }
        if (endDate == null){
            throw  new CustomValidationException("endDate는 필수입니다.");
        }

        return new ResponseEntity<>(new ResponseDto<>(200,"거래내역 조회 성공",transactionService.getTransactionType(id,account,type,startPage,endPage,startDate,endDate)),HttpStatus.OK);

    }
}
