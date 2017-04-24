package com.bounswe.bounswe2017group3;

/**
 * @author gokce
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private int errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public CustomException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public CustomException() {
        super();
    }
}
