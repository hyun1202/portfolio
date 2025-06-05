package com.numo.portfolio.category.comm.exception;

import com.numo.portfolio.comm.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATED_CATEGORY(HttpStatus.BAD_REQUEST, "중복된 카테고리입니다.");

    private final HttpStatus status;
    private final String message;

    CategoryErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
