package com.example.gradesys.exception;

public class Status440ManagerNotFound extends ErrorCodeException {


    private static final int CODE = 440;

    public Status440ManagerNotFound(String message, String type, String value) {
        super(CODE,message,"Manager Not Found by " +type+ ": " + value);
    }

    public Status440ManagerNotFound(Long id) {
        super(CODE, "Manager Not Found by id: " + id);
    }

    public Status440ManagerNotFound(String msg) {
        super(CODE, msg);
    }
}
