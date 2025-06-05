package com.numo.portfolio.comm.dto;

import com.numo.portfolio.comm.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Result<T> {
    private final boolean success;
    private final T data;
    private final List<ErrorCode> errors;

    public Result(boolean success, T data, ErrorCode errorCode) {
        this.success = success;
        this.data = data;
        this.errors = List.of(errorCode);
    }

    public static <T> Result<T> success(T category) {
        return new Result<>(true, category, List.of());
    }
    
    public static Result<?> failure(List<ErrorCode> errors) {
        return new Result<>(false, null, errors);
    }
}