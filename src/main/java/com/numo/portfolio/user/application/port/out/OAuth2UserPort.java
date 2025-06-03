package com.numo.portfolio.user.application.port.out;

import com.numo.portfolio.security.oauth2.info.OAuth2UserInfo;
import com.numo.portfolio.user.domain.User;

public interface OAuth2UserPort {
    User getOrSaveUser(OAuth2UserInfo oAuth2UserInfo);
}
