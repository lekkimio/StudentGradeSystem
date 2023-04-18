package com.example.gradesys.exception;

public class Status401UnauthorizedUser extends Status401Unauthorized{

    public Status401UnauthorizedUser(String msg) {
        super( "You are not allowed to access this action: " + msg);
    }
}
