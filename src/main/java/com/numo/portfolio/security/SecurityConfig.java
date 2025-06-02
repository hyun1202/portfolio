package com.numo.portfolio.security;

import com.numo.portfolio.security.handle.JwtAccessDeniedHandler;
import com.numo.portfolio.security.handle.JwtAuthenticationEntryPoint;
import com.numo.portfolio.security.jwt.JwtFilter;
import com.numo.portfolio.security.jwt.TokenProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

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

        http.addFilterAt(jwtFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    private Filter jwtFilter() {
        return new JwtFilter(tokenProvider);
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

    private AccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    private AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
}
