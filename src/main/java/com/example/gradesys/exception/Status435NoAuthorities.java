package com.example.gradesys.exception;

public class Status435NoAuthorities extends ErrorCodeException {

    private static final int CODE = 435;

//    public Status435NoAuthorities(String message) {
//        super(CODE, message);
//    }

    public Status435NoAuthorities(Object type) {
        super(CODE, "You are not allowed to access this action: " + type);

    }
}
