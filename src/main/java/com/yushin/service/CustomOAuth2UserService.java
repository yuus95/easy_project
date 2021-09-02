package com.yushin.service;


import com.yushin.domain.member.AuthProvider;
import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.handler.ex.CustomException;
import com.yushin.handler.ex.ErrorCode;
import com.yushin.jwt.TokenProvider;
import com.yushin.security.UserPrincipal;
import com.yushin.security.oauth.user.OAuth2UserInfo;
import com.yushin.security.oauth.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        System.out.println("1111111111111111");
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new CustomException(ErrorCode.CANNOT_PROVIDER);
        }
        Optional<Member> memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());


        Member member;
        if(memberOptional.isPresent()) {
            member = memberOptional.get();

            /**
             * 연동 OAuth 많을경우 참조
             */
//            if(!member.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//                throw new CustomException(ErrorCode.CANNOT_PROVIDER);
//            }

        } else {
            System.out.println("2222222222222222222222");

            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(member, oAuth2User.getAttributes());
    }
    private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Member member = Member.builder()
                .nickname(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .authority(Authority.ROLE_USER)
                .build();

        return memberRepository.save(member);
    }

}