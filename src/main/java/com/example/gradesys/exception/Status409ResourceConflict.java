package com.example.gradesys.exception;

public class Status409ResourceConflict extends ErrorCodeException {

    private static final int CODE = 409;

    public Status409ResourceConflict(String message, String type, String value) {
        super(CODE,message,type,value);
    }

    public Status409ResourceConflict(String msg) {
        super(CODE, msg);
    }

}
