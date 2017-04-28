package com.bounswe.bounswe2017group3;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -6936870003471737387L;
    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}