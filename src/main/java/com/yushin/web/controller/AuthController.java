package com.yushin.web.controller;


import com.yushin.service.AuthService;
import com.yushin.web.dto.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {

        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto,
                                          BindingResult bindingResult)//꼭 valid 옆에 넣기
    {
//        if(bindingResult.hasErrors()){
//            Map<String,String> errorMap = new HashMap<>();
//            for(FieldError error : bindingResult.getFieldErrors()){
//                errorMap.put(error.getField(),error.getDefaultMessage());
//            }
//        }
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}