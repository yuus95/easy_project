package com.yushin.security.oauth.user;


import com.yushin.domain.member.AuthProvider;
import com.yushin.handler.ex.CustomException;
import com.yushin.handler.ex.ErrorCode;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new CustomException(ErrorCode.CANNOT_PROVIDER);
        }
    }
}