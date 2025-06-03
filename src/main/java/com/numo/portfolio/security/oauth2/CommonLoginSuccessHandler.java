package com.numo.portfolio.security.oauth2;

import com.numo.portfolio.conf.PropertyConfig;
import com.numo.portfolio.security.jwt.TokenProvider;
import com.numo.portfolio.security.service.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static com.numo.portfolio.security.jwt.JwtConst.ACCESS_TOKEN_COOKIE;

@RequiredArgsConstructor
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final PropertyConfig propertyConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = tokenProvider.createTokenDto(userDetails.getUsername(), userDetails.getAuthorityList());
        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE, accessToken);

        accessTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);

        accessTokenCookie.isHttpOnly();

        String clientHost = propertyConfig.getClientHost();
        response.sendRedirect(clientHost + "/oauth/callback");
    }
}
