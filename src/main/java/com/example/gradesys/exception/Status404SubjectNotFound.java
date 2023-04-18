package com.example.gradesys.exception;

public class Status404SubjectNotFound extends Status404ResourceNotFound {

    public Status404SubjectNotFound(Long id) {
        super("Subject not found by id: " + id);
    }

}
