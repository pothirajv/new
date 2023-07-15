package com.panel.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.panel.model.EmployeeDetails;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

	String responseText;
	String employeeToken;
	EmployeeDetails employeeDetails;
	
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public String getEmployeeToken() {
		return employeeToken;
	}
	public void setEmployeeToken(String employeeToken) {
		this.employeeToken = employeeToken;
	}
	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	
	
	
}
