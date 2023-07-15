package com.panel.dto;

import java.util.LinkedHashMap;

public class AssignActivityCodeToProjectResponseDto {
	
	
    boolean Status;
	
	LinkedHashMap<String, Object> data;
	
	String code;
	
	String errorCode;
	
	String errorMessage;
	
	String message;

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

	public LinkedHashMap<String, Object> getData() {
		return data;
	}

	public void setData(LinkedHashMap<String, Object> data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	

}
