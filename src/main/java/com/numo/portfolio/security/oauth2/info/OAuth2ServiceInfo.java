package com.numo.portfolio.security.oauth2.info;

import java.util.Map;

public interface OAuth2ServiceInfo {
    OAuth2UserInfo getUserInfo(Map<String, Object> attributes);
}
