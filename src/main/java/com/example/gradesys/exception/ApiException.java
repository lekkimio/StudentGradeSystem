package com.example.gradesys.exception;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ApiException(String message, Integer code, ZonedDateTime timestamp) {

}
