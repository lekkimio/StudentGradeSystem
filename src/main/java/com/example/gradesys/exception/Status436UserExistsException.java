package com.example.gradesys.exception;

public class Status436UserExistsException extends ErrorCodeException{

    private static final int CODE = 436;

    public Status436UserExistsException( String username) {
        super(CODE,"User already exists with such username " + username );
    }
}
