package com.numo.portfolio.comm.dto;

import org.springframework.http.HttpStatus;

public record CommonResult<T> (
        boolean success,
        String message,
        T data
) {
}
