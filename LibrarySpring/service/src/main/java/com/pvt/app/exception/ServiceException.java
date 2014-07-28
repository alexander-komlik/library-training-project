package com.pvt.app.exception;

public class ServiceException extends Exception {

    public ServiceException(String msg) {
        super(msg);
    }

    public String getMessage() {
        return super.getMessage();
    }

}
