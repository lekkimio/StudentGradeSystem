package com.example.gradesys.exception;

public class Status409SubjectExistsException extends Status409ResourceConflict{

    public Status409SubjectExistsException(String name) {
        super("Subject already exists with such name " + name );
    }
}