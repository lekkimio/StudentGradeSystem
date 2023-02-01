package com.example.gradesys.exception;


public class Status434UserNotFound extends ErrorCodeException {

    private static final int CODE = 434;

    public Status434UserNotFound(String message, String type, String value) {
        super(CODE,message,"User Not Found by " +type+ ": " + value);
    }

    public Status434UserNotFound(Long id) {
        super(CODE, "User Not Found by id: " + id);
    }

    public Status434UserNotFound(String msg) {
        super(CODE, msg);
    }
}
