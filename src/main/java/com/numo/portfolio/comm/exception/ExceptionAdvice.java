package com.numo.portfolio.comm.exception;

import com.numo.portfolio.comm.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult<?>> handleException(Exception e) {
        log.error("exception handler: ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonResult<>(false, "실패했습니다.", null));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResult<?>> handleScheduleException(CustomException e) {
        log.error("custom exception: ", e);
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new CommonResult<>(false, errorCode.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult<?>> handleException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CommonResult<>(false, builder.toString(), null));
    }
}
