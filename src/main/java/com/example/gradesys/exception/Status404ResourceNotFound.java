package com.example.gradesys.exception;

public class Status404ResourceNotFound extends ErrorCodeException{


    private static final int CODE = 404;

    public Status404ResourceNotFound(String message, String type, String value) {
        super(CODE,message,type,value);
    }

    public Status404ResourceNotFound(String msg) {
        super(CODE, msg);
    }

}
