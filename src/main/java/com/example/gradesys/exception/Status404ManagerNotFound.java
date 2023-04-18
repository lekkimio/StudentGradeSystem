package com.example.gradesys.exception;

public class Status404ManagerNotFound extends Status404ResourceNotFound {


    public Status404ManagerNotFound(String msg){
        super(msg);
    }

    public Status404ManagerNotFound(Long id) {
        super("Manager Not Found by id: " + id);
    }




}
