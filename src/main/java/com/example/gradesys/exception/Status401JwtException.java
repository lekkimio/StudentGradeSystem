package com.example.gradesys.exception;

public class Status401JwtException extends Status401Unauthorized {


    public Status401JwtException() {
        super("Jwt token is expired or invalid");
    }
}
