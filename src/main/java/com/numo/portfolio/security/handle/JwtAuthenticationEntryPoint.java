package com.numo.portfolio.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numo.portfolio.comm.dto.CommonResult;
import com.numo.portfolio.comm.exception.CustomException;
import com.numo.portfolio.user.comm.exception.UserErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("401 unauthorized, {}", request.getRequestURI());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CommonResult<Object> res = new CommonResult<>(false, "자격증명에 실패했습니다.", null);
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
    }
}
