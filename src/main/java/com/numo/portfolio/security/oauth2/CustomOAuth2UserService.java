package com.numo.portfolio.security.oauth2;

import com.numo.portfolio.security.oauth2.info.*;
import com.numo.portfolio.security.service.UserDetailsImpl;
import com.numo.portfolio.user.application.port.out.OAuth2UserPort;
import com.numo.portfolio.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OAuth2UserPort oAuth2UserPort;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        // OAuth2 정보를 가져온다.
        OAuth2User oAuth2User = service.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String serviceName = userRequest.getClientRegistration().getClientName();
        OAuth2ServiceInfo oAuth2ServiceInfo = getOAuth2UserInfo(serviceName);
        OAuth2UserInfo oAuth2UserInfo = oAuth2ServiceInfo.getUserInfo(attributes);

        User user = saveUserOrUpdate(oAuth2UserInfo, serviceName);

        if (!user.isActivatedUser()) {
            OAuth2Error oauth2Error = new OAuth2Error("AU02", "탈퇴한 유저입니다.", serviceName);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        return new UserDetailsImpl(user, attributes);
    }

    private User saveUserOrUpdate(OAuth2UserInfo oAuth2UserInfo, String serviceName) {
        try {
            return oAuth2UserPort.getOrSaveUser(oAuth2UserInfo);
        } catch (Exception e) {
           OAuth2Error oauth2Error = new OAuth2Error("AU01", "사용자 정보 저장에 실패했습니다.", serviceName);
           throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), e);
        }
    }

    private OAuth2ServiceInfo getOAuth2UserInfo(String serviceName) {
        return switch (serviceName) {
            case "kakao" -> new KaKaoServiceInfo();
            case "google" -> new GoogleServiceInfo();
            default -> throw new IllegalStateException("Unexpected value: " + serviceName);
        };
    }

}
