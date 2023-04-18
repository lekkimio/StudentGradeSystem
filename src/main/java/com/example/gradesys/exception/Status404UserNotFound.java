package com.example.gradesys.exception;


public class Status404UserNotFound extends Status404ResourceNotFound {
    public Status404UserNotFound(Long id) {
        super("User not found by id: " + id);
    }


}
