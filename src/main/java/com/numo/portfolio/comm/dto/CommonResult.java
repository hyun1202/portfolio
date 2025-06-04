package com.numo.portfolio.comm.dto;

import org.springframework.http.HttpStatus;

public record CommonResult<T> (
        HttpStatus status,
        String message,
        T data
) {
}
