package com.yushin.web.controller;

import com.yushin.handler.ex.CustomValidationException;
import com.yushin.handler.ex.ValidationErrorResponse;
import com.yushin.service.MemberAccountService;
import com.yushin.web.dto.memberAccount.MemberAccountRequestDto;
import com.yushin.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberAccountController {
    private final MemberAccountService memberAccountService;


    /**
     * 계좌등록 api 5개이상 등록 못하게 하기
     *
     */

    @PostMapping("/api/account-register")
    public ResponseEntity<?> registAccount(@Valid @RequestBody MemberAccountRequestDto memberAccountRequestDto, BindingResult bindingResult){
        memberAccountService.register(memberAccountRequestDto);

        return new ResponseEntity<>(new ResponseDto<>(200, "은행 계좌 등록 성공!", null), HttpStatus.OK);
    }

    @GetMapping("/api/account")
    public ResponseEntity<?> oneAccount(@RequestParam("id") long id, @RequestParam("account") String account){
        if (account == null){
            throw  new CustomValidationException("계좌를 입력해주세요.");
        }

        return new ResponseEntity<>(new ResponseDto<>(200,"은행 계좌 조회 성공",memberAccountService.getOneAccount(id,account)),HttpStatus.OK);
    }

    @GetMapping("/api/all-account/{id}")
    public ResponseEntity<ResponseDto> AllAccount(@PathVariable long id){
        return new ResponseEntity<ResponseDto>(new ResponseDto<>(200,"은행 모든 계좌 조회 성공",memberAccountService.AllAccount(id)),HttpStatus.OK);
    }

    @DeleteMapping("/api/account")
    public ResponseEntity<?> deleteAccount(@RequestParam("id") long id, @RequestParam("account") String account){
        if (account == null){
            throw  new CustomValidationException("계좌를 입력해주세요.");
        }
        memberAccountService.DeleteAccount(id,account);
        return new ResponseEntity<>(new ResponseDto<>(200,"은행 계좌 삭제 성공",null),HttpStatus.OK);
    }

}
