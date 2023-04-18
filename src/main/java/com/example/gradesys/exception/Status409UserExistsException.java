package com.example.gradesys.exception;

public class Status409UserExistsException extends Status409ResourceConflict{

    public Status409UserExistsException(String username) {
        super("User already exists with such username " + username );
    }
}
