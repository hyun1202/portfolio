package com.numo.portfolio.security.oauth2.info;

import lombok.Builder;

@Builder
public record OAuth2UserInfo(
        String socialId,
        String clientName,
        String email,
        String nickname
) {
}
