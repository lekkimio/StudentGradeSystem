package com.example.gradesys.exception;

public class Status437SubjectNotFound extends ErrorCodeException {

    private static final int CODE = 437;

    public Status437SubjectNotFound(String message, String type, String value) {
        super(CODE,message,"Subject Not Found by " +type+ ": " + value);
    }

    public Status437SubjectNotFound(Long id) {
        super(CODE, "Subject Not Found by id: " + id);
    }

    public Status437SubjectNotFound(String msg) {
        super(CODE, msg);
    }
}
