package com.example.gradesys.exception;

public class Status404ResultNotFound extends Status404ResourceNotFound {

    public Status404ResultNotFound(Long id) {
        super("Result Not Found by id: " + id);
    }

}