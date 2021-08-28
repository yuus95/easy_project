package com.yushin.web.controller;


import com.yushin.service.AuthService;
import com.yushin.web.dto.member.*;
import com.yushin.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {

        return new ResponseEntity<>(new ResponseDto(200,"회원가입 성공",authService.signup(memberRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto,
                                          BindingResult bindingResult)//꼭 valid 옆에 넣기
    {
//        if(bindingResult.hasErrors()){
//            Map<String,String> errorMap = new HashMap<>();
//            for(FieldError error : bindingResult.getFieldErrors()){
//                errorMap.put(error.getField(),error.getDefaultMessage());
//            }
//        }

        return new ResponseEntity<>(new ResponseDto(200,"로그인 성공",authService.login(loginDto)), HttpStatus.OK);

    }

    @GetMapping("/")

    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(200,"토큰 재발행 성공",authService.reissue(tokenRequestDto)), HttpStatus.OK);
    }
}