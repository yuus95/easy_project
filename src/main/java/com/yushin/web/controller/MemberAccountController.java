package com.yushin.web.controller;

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

    @PostMapping("/api/account-register/{id}")
    public ResponseEntity<?> registAccount(@Valid @RequestBody MemberAccountRequestDto memberAccountRequestDto, BindingResult bindingResult, @PathVariable long id){
        memberAccountService.register(memberAccountRequestDto,id);

        return new ResponseEntity<>(new ResponseDto<>(200, "은행 계좌 등록 성공!", null), HttpStatus.OK);
    }

    @GetMapping("/api/account")
    public ResponseEntity<?> oneAccount(@RequestParam("id") long id, @RequestParam("account") String account){
        if (account == null){
            ValidationErrorResponse er = new ValidationErrorResponse(400,"계좌를 입력해주세요");
            return new ResponseEntity<ValidationErrorResponse>(er,HttpStatus.BAD_REQUEST);
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
            ValidationErrorResponse er = new ValidationErrorResponse(400,"계좌를 입력해주세요");
            return new ResponseEntity<ValidationErrorResponse>(er,HttpStatus.BAD_REQUEST);
        }
        memberAccountService.DeleteAccount(id,account);
        return new ResponseEntity<>(new ResponseDto<>(200,"은행 계좌 삭제 성공",null),HttpStatus.OK);
    }

}
