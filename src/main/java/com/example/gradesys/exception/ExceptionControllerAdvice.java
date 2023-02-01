package com.example.gradesys.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ApiException> handleAlreadyExistException(ErrorCodeException ex) {
        return ResponseEntity.status(ex.getCode()).body(ApiException.builder()
                .message(ex.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .code(ex.getCode()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Got exception: ", ex);
        return new ResponseEntity<>(ExceptionUtils.getStackTrace(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

