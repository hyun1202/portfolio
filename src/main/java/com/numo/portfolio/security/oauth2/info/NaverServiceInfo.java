package com.numo.portfolio.security.oauth2.info;

import java.util.Map;

public class NaverServiceInfo implements OAuth2ServiceInfo {
    @Override
    public OAuth2UserInfo getUserInfo(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String socialId = String.valueOf(response.get("id"));
        String nickname = String.valueOf(response.get("nickname"));
        String email = String.valueOf(response.get("email"));

        return OAuth2UserInfo.builder()
                .socialId(socialId)
                .email(email)
                .nickname(nickname)
                .clientName("naver")
                .build();
    }
}
