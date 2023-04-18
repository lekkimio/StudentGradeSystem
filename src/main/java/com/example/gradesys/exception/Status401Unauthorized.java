package com.example.gradesys.exception;

public class Status401Unauthorized extends ErrorCodeException {

    private static final int CODE = 401;

    public Status401Unauthorized(String message, String type, String value) {
        super(CODE,message,type,value);
    }

    public Status401Unauthorized(String msg) {
        super(CODE, msg);
    }


}
