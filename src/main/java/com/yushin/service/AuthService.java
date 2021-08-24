package com.yushin.service;

import com.yushin.domain.refresh.RefreshToken;
import com.yushin.domain.refresh.RefreshTokenRepository;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.handler.ex.CustomException;
import com.yushin.jwt.TokenProvider;
import com.yushin.web.dto.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.yushin.handler.ex.ErrorCode.*;

@Service
@RequiredArgsConstructor // final이 걸려있는 모든생성자를 만들어준다 DI를 할 떄 사용
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional // write (insert,update,delete)할 때 사용
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new CustomException(DUPLICATE_MEMBER);
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        Optional<Member> byEmail =memberRepository.findByEmail(loginDto.getEmail());

        if (byEmail.isEmpty()){
            throw new CustomException(NOT_MATCHED_EMAIL);
        }

        String password = byEmail.get().getPassword();
        if (!passwordEncoder.matches(loginDto.getPassword(),password)){
            throw new CustomException(NOT_MATCHED_PASSWORD);
        }


        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        //     인증에 성공하면 Authentication을 SecurityContextHolder에 저장할 수 있는 객체로 만들어진다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .idKey(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = (RefreshToken) refreshTokenRepository.findByIdKey(authentication.getName())
                .orElseThrow(() -> new CustomException(REFRESH_TOKEN_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(MISMATCH_REFRESH_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}