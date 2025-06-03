package com.numo.portfolio.security;

import com.numo.portfolio.conf.PropertyConfig;
import com.numo.portfolio.security.handle.JwtAccessDeniedHandler;
import com.numo.portfolio.security.handle.JwtAuthenticationEntryPoint;
import com.numo.portfolio.security.jwt.JwtFilter;
import com.numo.portfolio.security.jwt.TokenProvider;
import com.numo.portfolio.security.oauth2.CommonLoginFailureHandler;
import com.numo.portfolio.security.oauth2.CommonLoginSuccessHandler;
import com.numo.portfolio.security.oauth2.CustomOAuth2UserService;
import com.numo.portfolio.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final PropertyConfig propertyConfig;
    private final CustomOAuth2UserService CustomOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        http.exceptionHandling(handlingConfigurer -> {
            handlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint());
            handlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler());
        });

        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> {
            authorize.anyRequest().permitAll();
        });

        http.oauth2Login(configurer -> configurer
                .authorizationEndpoint(config -> config.authorizationRequestRepository(authorizationRequestRepository()))
                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                        .baseUri("/oauth2/callback/*")
                )
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(CustomOAuth2UserService)
                )
                .successHandler(commonLoginSuccessHandler())
                .failureHandler(commonLoginFailureHandler())
        );

        http.addFilterAt(jwtFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        String[] list = {
                "http://localhost:3000"
        };
        config.setAllowCredentials(true);   //내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
        config.setAllowedOriginPatterns(Arrays.asList(list));
        config.addAllowedHeader("*");   // 모든 header에 응답 허용
        config.addAllowedMethod("*");   //모든 post, get, put, delete, patch 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private AuthenticationFailureHandler commonLoginFailureHandler() {
        return new CommonLoginFailureHandler(propertyConfig);
    }

    private AuthenticationSuccessHandler commonLoginSuccessHandler() {
        return new CommonLoginSuccessHandler(tokenProvider, propertyConfig);
    }

    private Filter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }

    private AccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    private AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
}
