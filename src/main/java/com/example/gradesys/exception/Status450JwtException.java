package com.example.gradesys.exception;

public class Status450JwtException extends ErrorCodeException{

    private final static int CODE = 450;

    public Status450JwtException() {
        super(CODE, "Jwt token is expired or invalid");
    }
}
