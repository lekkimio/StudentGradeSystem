package com.example.gradesys.exception;

public class Status439ResultNotFound extends ErrorCodeException {

    private static final int CODE = 439;

    public Status439ResultNotFound(String message, String type, String value) {
        super(CODE,message,"Result Not Found by " +type+ ": " + value);
    }

    public Status439ResultNotFound(Long id) {
        super(CODE, "Result Not Found by id: " + id);
    }

    public Status439ResultNotFound(String msg) {
        super(CODE, msg);
    }
}