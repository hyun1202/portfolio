package com.numo.portfolio.security.oauth2.info;

import java.util.Map;

public class GoogleServiceInfo implements OAuth2ServiceInfo {
    @Override
    public OAuth2UserInfo getUserInfo(Map<String, Object> attributes) {
        String socialId = String.valueOf(attributes.get("sub"));
        String nickname = String.valueOf(attributes.get("name"));
        String email = String.valueOf(attributes.get("email"));

        return OAuth2UserInfo.builder()
                .socialId(socialId)
                .email(email)
                .nickname(nickname)
                .clientName("kakao")
                .build();
    }

}
