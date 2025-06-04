package com.numo.portfolio.user.comm.exception;

import com.numo.portfolio.comm.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    OAUTH_USER_SAVE_FAILED(HttpStatus.UNAUTHORIZED, "사용자 정보 저장에 실패했습니다."),
    WITHDRAW_USER(HttpStatus.UNAUTHORIZED, "탈퇴한 유저입니다."),
    OAUTH_SERVICE_NOT_SUPPORT(HttpStatus.NOT_FOUND, "해당하는 서비스는 이용할 수 없습니다."),
    EXISTS_USER(HttpStatus.BAD_REQUEST, "이미 가입된 유저입니다."),
    EXISTS_DOMAIN(HttpStatus.BAD_REQUEST, "이미 사용하고 있는 도메인입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    UserErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
