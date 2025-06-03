package com.numo.portfolio.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.numo.portfolio.security.jwt.JwtConst.ACCESS_TOKEN_COOKIE;
import static com.numo.portfolio.security.jwt.JwtConst.AUTHORIZATION_HEADER;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 토큰 정보 추출
        String token = resolveTokenFromHeader(request);

        // 2. 쿠키에서 토큰 정보 추출
        if (token == null) {
            token = resolveTokenFromCookie(request.getCookies());
        }

        String requestURI = request.getRequestURI();
        log.debug("requestURI: {}", requestURI);

        // 유효성 검증
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}'인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            String socialId = tokenProvider.getSocialId(token);
            log.debug("login Id: {}", socialId);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 헤더에서 accessToken 정보를 추출한다.
     * @param request http 요청 정보
     * @return 토큰 정보
     */
    private String resolveTokenFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 쿠키에서 accessToken 정보를 추출한다.
     * @param cookies 쿠키
     * @return 토큰 정보
     */
    private String resolveTokenFromCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_COOKIE))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
