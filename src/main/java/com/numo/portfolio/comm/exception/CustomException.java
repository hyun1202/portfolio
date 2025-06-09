package com.numo.portfolio.comm.exception;

public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(String message) {
        this(message, (Throwable) null);
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
    }

    public CustomException(ErrorCode errorCode) {
        this(errorCode, null);
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode) {
        this(message, errorCode, null);
    }

    public CustomException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(), e);
    }

    public CustomException(String message, ErrorCode errorCode, Throwable e) {
        super("error message: " + message + ", errorCode: " + errorCode.getMessage(), e);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
