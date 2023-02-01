package com.example.gradesys.exception;

public class Status438SubjectExistsException extends ErrorCodeException{

    private static final int CODE = 438;

    public Status438SubjectExistsException( String name) {
        super(CODE,"Subject already exists with such name " + name );
    }
}