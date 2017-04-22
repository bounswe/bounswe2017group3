/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bounswe.bounswe2017group3;

/**
 *
 * @author gokce
 */
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	private String errorMessage;
        private String errorCode;
	public String getErrorMessage() {
		return errorMessage;
	}
        public String getErrorCode(){
            return errorCode;
        }
	public CustomException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
                this.errorCode = errorCode;
        }
	public CustomException() {
		super();
	}
}
